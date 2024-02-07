<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">客户英文名</label>
        <el-input v-model="query.clientName" clearable placeholder="客户英文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">客户中文名</label>
        <el-input v-model="query.clientNameC" clearable placeholder="客户中文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">客户标签</label>
        <el-select
          v-model="query.clientTag"
          clearable
          size="small"
          placeholder="类别"
          class="filter-item"
          style="width: 90px"
          @change="crud.toQuery"
        >
          <el-option
            v-for="item in dict.studio_client_tag"
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
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="客户英文名" prop="clientName">
            <el-input v-model="form.clientName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="客户中文名" prop="clientNameC">
            <el-input v-model="form.clientNameC" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="客户标签">
            <el-select v-model="form.clientTag" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.studio_client_tag"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="客户手机号">
            <el-input v-model="form.clientPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="客户邮箱">
            <el-input v-model="form.clientMail" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55px" />
        <el-table-column prop="clientId" label="客户id" />
        <el-table-column prop="clientName" label="客户英文名" />
        <el-table-column prop="clientNameC" label="客户中文名" />
        <el-table-column prop="clientTag" label="客户标签">
          <template slot-scope="scope">
            <el-tag
              type="success"
              effect="light"
            >{{ scope.row.clientTag }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="clientPhone" label="客户手机号" />
        <el-table-column prop="clientMail" label="客户邮箱" />
        <el-table-column v-if="checkPer(['admin','studioClient:edit','studioClient:del'])" label="操作" width="150px" align="center">
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
import crudStudioClient from '@/api/studioClient'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { clientId: null, clientName: null, clientNameC: null, clientTag: null, clientPhone: null, clientMail: null, createTime: null, updateTime: null }
export default {
  name: 'StudioClient',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['studio_client_tag'],
  cruds() {
    return CRUD({ title: '工作室客户', url: 'api/studioClient', idField: 'clientId', sort: 'clientId,desc', crudMethod: { ...crudStudioClient }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'studioClient:add'],
        edit: ['admin', 'studioClient:edit'],
        del: ['admin', 'studioClient:del']
      },
      rules: {
        clientName: [
          { required: true, message: '客户英文名不能为空', trigger: 'blur' }
        ],
        clientNameC: [
          { required: true, message: '客户中文名不能为空', trigger: 'blur' }
        ],
        clientTag: [
          { required: true, message: '客户类别不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'clientName', display_name: '客户英文名' },
        { key: 'clientNameC', display_name: '客户中文名' },
        { key: 'clientTag', display_name: '客户标签' }
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
