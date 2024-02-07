<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">作品id</label>
        <el-input v-model="query.workId" clearable placeholder="作品id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">作品尺寸</label>
        <el-select
          v-model="query.workStatus"
          clearable
          size="small"
          placeholder="类别"
          class="filter-item"
          style="width: 90px"
          @change="crud.toQuery"
        >
          <el-option
            v-for="item in dict.studio_work_image_size"
            :key="item.id"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="作品id" prop="workId">
            <el-input v-model="form.workId" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品图片" prop="workImage">
            <el-upload
              action="/api/studioImage/upload"
              list-type="picture-card"
              :on-success="handleSuccess"
              :on-error="handleError"
              :on-exceed="handleExceed"
              :before-upload="beforeUpload"
              :on-file-click="handleFileClick"
              :limit="1"
            >
              <i class="el-icon-plus" />
            </el-upload>
          </el-form-item>
          <el-form-item label="作品尺寸" prop="workStatus">
            <el-select v-model="form.workStatus" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.studio_work_image_size"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="作品图片高度">
            <el-input v-model="form.workImageHeight" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品图片宽度">
            <el-input v-model="form.workImageWidth" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品x坐标">
            <el-input v-model="form.workOffsetX" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品y坐标">
            <el-input v-model="form.workOffsetY" style="width: 340px;" />
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>

      </el-dialog>

      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="记录id" />
        <el-table-column prop="workId" label="作品id" />
        <el-table-column prop="workImage" label="作品图片">
          <template scope="scope">
            <el-popover placement="right" trigger="click" @after-leave="reload"> <!--trigger属性值：hover、click、focus 和 manual-->
              <div style="margin-bottom: 5px;text-align: center;">
                <label class="axis">点击图片拾取坐标</label>
              </div>
              <div>
                <img :src="require('@/assets/images/'+scope.row.workImage)" style="width: 400px;height: 400px" @click="onImageLoad">
                <canvas ref="canvas" @mousedown="onCanvasMouseDown" />
              </div>
              <div style="margin-top: 5px;text-align: center;">
                <label>横坐标:</label>&nbsp;&nbsp;<el-tag type="danger" effect="pain">{{ scope.row.workOffsetX }}</el-tag>
                <label>纵坐标:</label>&nbsp;&nbsp;<el-tag type="danger" effect="pain"> {{ scope.row.workOffsetY }}</el-tag>
              </div>
              <img slot="reference" :src="require('@/assets/images/'+scope.row.workImage)" style="width: 70px;height: 70px; cursor:pointer">
            </el-popover>
          </template>

        </el-table-column>
        <el-table-column prop="workStatus" label="作品尺寸">
          <template v-slot="scope">
            <el-tag type="success" effect="dark">{{ scope.row.workStatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="workImageHeight" label="作品图片高度" />
        <el-table-column prop="workImageWidth" label="作品图片宽度" />
        <el-table-column prop="workOffsetX" label="作品x坐标" />
        <el-table-column prop="workOffsetY" label="作品y坐标" />
        <el-table-column v-if="checkPer(['admin','studioWorkImage:edit','studioWorkImage:del'])" label="操作" width="250px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>

        </el-table-column>
      </el-table>
      <pagination />
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import crudStudioWorkImage from '@/api/studioWorkImage'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, workId: null, workImage: null, workStatus: null, workOffsetX: null, workOffsetY: null, workImageHeight: null, workImageWidth: null, createTime: null, updateTime: null }
export default {
  name: 'StudioWorkImage',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['studio_work_image_size'],
  cruds() {
    return CRUD({ title: '工作室作品图', url: 'api/studioWorkImage', idField: 'id', sort: 'id,desc', crudMethod: { ...crudStudioWorkImage }})
  },
  data() {
    return {
      data() {
        return {
          dialogVisible: false,
          dialogImageUrl: '',
          imageWidth: null, // 存储图片的宽度
          imageHeight: null, // 存储图片的高度
          imageOffsetX: null, // 存储图片的x轴偏移量
          imageOffsetY: null, // 存储图片的y轴偏移量
          canvas: null,
          ctx: null

        }
      },
      permission: {
        add: ['admin', 'studioWorkImage:add'],
        edit: ['admin', 'studioWorkImage:edit'],
        del: ['admin', 'studioWorkImage:del']
      },
      rules: {
        workId: [
          { required: true, message: '作品id不能为空', trigger: 'blur' }
        ],
        workImage: [
          { required: true, message: '图片地址不能为空', trigger: 'blur' }
        ],
        workStatus: [
          { required: true, message: '作品尺寸不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'workId', display_name: '作品id' },
        { key: 'workStatus', display_name: '作品尺寸' }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'baseApi',
      'fileUploadApi'
    ])
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    handleSuccess(response) {
      this.form.workImage = response.data.path
      console.log('图片地址', this.form.workImage)
      this.uploadVisible = false
      // 获取图片的真实宽度和高度
      this.form.workImageHeight = response.data.height
      this.form.workImageWidth = response.data.width
      this.fileList.name = response.data.path
      this.fileList.url = '@/assets/images/' + response.data.path
      this.$notify({
        title: '上传成功!',
        message: '',
        type: 'success'
      })
    },
    handleError(response) {
      this.$notify.error({
        title: '上传失败!',
        message: ''
      })
    },
    handleExceed(file) {
      this.$notify({
        title: '最多上传一张图片哦！',
        message: '',
        type: 'warning'
      })
    },
    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isLt50MB = file.size / 1024 < 50 * 1024 * 1024

      if (!isJPG && !isPNG) {
        this.$message.error('只能上传jpg/png文件!')
      }
      if (!isLt50MB) {
        this.$message.error('上传图片大小不能超过50MB!')
      }

      return (isJPG || isPNG) && isLt50MB
    },
    handleFileClick(file) {
      // 当文件缩略图被点击时
      console.log('文件被点击', file)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    getImagePosition(row, event) {
      console.log('开始拾取')
      if (event && event.target) {
        const rect = event.target.getBoundingClientRect()
        const clickPosition = {
          x: event.clientX - rect.left,
          y: event.clientY - rect.top
        }
        row.workOffsetX = clickPosition.x
        row.workOffsetY = clickPosition.y
        console.log('Click Position:', clickPosition)
      } else {
        console.log('点击事件不存在')
      }
      this.$notify({
        title: '坐标拾取成功!',
        message: '',
        type: 'success'
      })
    },
    reload() {
      this.$router.go(0)
    },
    onImageLoad() {
      this.$nextTick(() => {
        this.canvas = this.$refs.canvas
        this.canvas.width = this.$refs.image.width
        this.canvas.height = this.$refs.image.height
        this.ctx = this.canvas.getContext('2d')
        this.ctx.drawImage(this.$refs.image, 0, 0)
        this.image = this.ctx.getImageData(0, 0, this.canvas.width, this.canvas.height)
      })
    },
    onCanvasMouseDown(event) {
      const popoverRect = this.$refs.popover.$el.getBoundingClientRect()
      const canvasRect = this.$refs.canvas.getBoundingClientRect()
      const mouseX = event.clientX - popoverRect.left - canvasRect.left
      const mouseY = event.clientY - popoverRect.top - canvasRect.top

      // 获取像素颜色
      const pixel = this.ctx.getImageData(mouseX, mouseY, 1, 1).data

      // 输出颜色信息
      console.log('Pixel Color:', pixel)

      // 在控制台输出坐标
      console.log('Canvas Coordinates:', mouseX, mouseY)

      // 获取图片坐标
      const imageX = mouseX * (this.canvas.width / canvasRect.width)
      const imageY = mouseY * (this.canvas.height / canvasRect.height)

      // 输出图片坐标
      console.log('Image Coordinates:', imageX, imageY)
    }
  }
}
</script>

<style scoped>
.axis {
  font-size: 17px;
}
canvas {
  position: absolute;
  top: 0;
  left: 0;
}
</style>
