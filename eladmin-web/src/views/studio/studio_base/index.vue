<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="110px">
          <el-form-item label="工作室英文名" prop="studioName">
            <el-input v-model="form.studioName" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室中文名" prop="studioNamec">
            <el-input v-model="form.studioNameC" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室英文描述">
            <el-input v-model="form.studioDes" type="textarea" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室中文描述">
            <el-input v-model="form.studioDesC" type="textarea" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室英文地址">
            <el-input v-model="form.studioLoc" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室中文地址">
            <el-input v-model="form.studioLocC" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室邮箱">
            <el-input v-model="form.studioMail" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室ig">
            <el-input v-model="form.studioIg" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室小红书">
            <el-input v-model="form.studioRed" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室经度">
            <el-input v-model="form.studioLocLon" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="工作室纬度">
            <el-input v-model="form.studioLocLat" style="width: 300px;" />
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
        <el-table-column prop="studioId" label="工作室id" />
        <el-table-column prop="studioName" label="工作室英文名" />
        <el-table-column prop="studioNameC" label="工作室中文名" />
        <el-table-column prop="studioDes" label="工作室英文描述" :show-overflow-tooltip="true">
          <template slot-scope="{row}">
            <span>{{ row.studioDes }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="studioDesC" label="工作室中文描述" :show-overflow-tooltip="true">
          <template slot-scope="{row}">
            <span>{{ row.studioDesC }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="studioLoc" label="工作室英文地址" />
        <el-table-column prop="studioLocC" label="工作室中文地址" />
        <el-table-column prop="studioMail" label="工作室邮箱" />
        <el-table-column prop="studioIg" label="工作室ig" />
        <el-table-column prop="studioRed" label="工作室小红书" />
        <el-table-column prop="studioLocLon" label="工作室经度" />
        <el-table-column prop="studioLocLat" label="工作室纬度" />
        <el-table-column v-if="checkPer(['admin','studioBase:edit','studioBase:del'])" label="操作" width="150px" align="center">
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
import crudStudioBase from '@/api/studioBase'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation.vue'
import crudOperation from '@crud/CRUD.operation.vue'
import udOperation from '@crud/UD.operation.vue'
import pagination from '@crud/Pagination.vue'

const defaultForm = { studioId: null, studioName: null, studioNameC: null, studioDes: null, studioDesC: null, studioLoc: null, studioLocC: null, studioLocLon: null, studioLocLat: null, studioMail: null, studioIg: null, studioRed: null, createTime: null, updateTime: null }
export default {
  name: 'StudioBase',
  // eslint-disable-next-line vue/no-unused-components
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '工作室基本信息', url: 'api/studioBase', idField: 'studioId', sort: 'studioId,desc', crudMethod: { ...crudStudioBase }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'studioBase:add'],
        edit: ['admin', 'studioBase:edit'],
        del: ['admin', 'studioBase:del']
      },
      rules: {
        studioId: [
          { required: true, message: '工作室id不能为空', trigger: 'blur' }
        ],
        studioName: [
          { required: true, message: '工作室英文名不能为空', trigger: 'blur' }
        ],
        studioNameC: [
          { required: true, message: '工作室中文名不能为空', trigger: 'blur' }
        ]
      }
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
.el-tooltip__popper{font-size: 14px; max-width:40% }/* 设置显示隐藏部分内容，按40%显示 */
</style>
