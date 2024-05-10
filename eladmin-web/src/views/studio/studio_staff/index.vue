<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">员工英文名</label>
        <el-input v-model="query.staffName" clearable placeholder="员工英文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">员工中文名</label>
        <el-input v-model="query.staffNameC" clearable placeholder="员工中文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="员工英文名" prop="staffName">
            <el-input v-model="form.staffName" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="员工中文名" prop="staffNameC">
            <el-input v-model="form.staffNameC" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="员工职称" prop="staffTitle">
            <el-input v-model="form.staffTitle" style="width: 300px;" />
          </el-form-item>
          <el-form-item label="员工性别" prop="staffSex">
            <el-radio v-for="item in dict.studio_staff_sex" :key="item.id" v-model="form.staffSex" :label="item.value">{{ item.label }}</el-radio>
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
        <el-table-column prop="staffId" label="员工id" />
        <el-table-column prop="staffName" label="员工英文名" />
        <el-table-column prop="staffNameC" label="员工中文名" />
        <el-table-column prop="staffTitle" label="员工职称">
          <template slot-scope="scope">
            <el-tag
              type="success"
              effect="dark"
            >{{ scope.row.staffTitle }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="staffSex" label="员工性别">
          <template slot-scope="scope">
            {{ dict.label.studio_staff_sex[scope.row.staffSex] }}
          </template>
        </el-table-column>

        <el-table-column v-if="checkPer(['admin','studioStaff:edit','studioStaff:del'])" label="操作" width="150px" align="center">
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
import crudStudioStaff from '@/api/studioStaff'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { staffId: null, staffName: null, staffNameC: null, staffSex: null, staffTitle: null, staffStudioId: null, createTime: null, updateTime: null }
export default {
  name: 'StudioStaff',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['studio_staff_sex'],
  cruds() {
    return CRUD({ title: '工作室员工', url: 'api/studioStaff', idField: 'staffId', sort: 'staffId,desc', crudMethod: { ...crudStudioStaff }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'studioStaff:add'],
        edit: ['admin', 'studioStaff:edit'],
        del: ['admin', 'studioStaff:del']
      },
      rules: {
        staffName: [
          { required: true, message: '员工英文名不能为空', trigger: 'blur' }
        ],
        staffNameC: [
          { required: true, message: '员工中文名不能为空', trigger: 'blur' }
        ],
        staffSex: [
          { required: true, message: '员工性别不能为空', trigger: 'blur' }
        ],
        staffTitle: [
          { required: true, message: '员工职称不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'staffName', display_name: '员工英文名' },
        { key: 'staffNameC', display_name: '员工中文名' }
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
