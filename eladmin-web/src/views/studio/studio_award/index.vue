<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">奖项名称</label>
        <el-input v-model="query.awardName" clearable placeholder="奖项名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="奖项名称">
            <el-input v-model="form.awardName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="奖项分类">
            <el-input v-model="form.awardCategory" style="width: 370px;" />
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
        <el-table-column prop="awardId" label="奖项id" />
        <el-table-column prop="awardName" label="奖项名称" />
        <el-table-column prop="awardCategory" label="奖项分类" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column v-if="checkPer(['admin','studioAward:edit','studioAward:del'])" label="操作" width="150px" align="center">
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
import crudStudioAward from '@/api/studioAward'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation.vue'
import crudOperation from '@crud/CRUD.operation.vue'
import udOperation from '@crud/UD.operation.vue'
import pagination from '@crud/Pagination.vue'

const defaultForm = { awardId: null, awardName: null, awardCategory: null, createTime: null, updateTime: null }
export default {
  name: 'StudioAward',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '工作室奖项', url: 'api/studioAward', idField: 'awardId', sort: 'awardId,desc', crudMethod: { ...crudStudioAward }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'studioAward:add'],
        edit: ['admin', 'studioAward:edit'],
        del: ['admin', 'studioAward:del']
      },
      rules: {
        awardId: [
          { required: true, message: '奖项id不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'awardName', display_name: '奖项名称' }
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
