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
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="作品id" prop="workId">
            <el-input v-model="form.workId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="图片地址" prop="workImage">
            <el-upload
              action="https://jsonplaceholder.typicode.com/posts/"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
            >
              <img v-if="form.workImage" :src="form.workImage" class="avatar" width="100%">
              <i v-else class="el-icon-plus avatar-uploader-icon" />
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
          <el-form-item label="作品x坐标">
            <el-input v-model="form.workOffsetX" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="作品y坐标">
            <el-input v-model="form.workOffsetY" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="作品图片高度">
            <el-input v-model="form.workImageHeight" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="作品图片宽度">
            <el-input v-model="form.workImageWidth" style="width: 370px;" />
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
        <el-table-column prop="workImage" label="图片地址">
          <template slot-scope="scope">
            <el-image
              style="width: 70px; height: 70px"
              :src="scope.row.workImage"
              fit="cover"
              @click.native="showBigImage(scope.row.workImage)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="workStatus" label="作品尺寸">
          <template slot-scope="scope">
            <el-tag type="success" effect="dark">{{ scope.row.workStatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="workOffsetX" label="作品x坐标" />
        <el-table-column prop="workOffsetY" label="作品y坐标" />
        <el-table-column prop="workImageHeight" label="作品图片高度" />
        <el-table-column prop="workImageWidth" label="作品图片宽度" />
        <el-table-column v-if="checkPer(['admin','studioWorkImage:edit','studioWorkImage:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
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
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
