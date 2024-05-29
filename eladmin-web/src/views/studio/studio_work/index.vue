<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">作品英文名</label>
        <el-input v-model="query.workName" clearable placeholder="作品英文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">作品中文名</label>
        <el-input v-model="query.workNameC" clearable placeholder="作品中文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">作品客户</label>
        <el-input v-model="query.workClient" clearable placeholder="作品客户" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">作品类别</label>
        <el-select
          v-model="query.workType"
          clearable
          size="small"
          placeholder="类别"
          class="filter-item"
          style="width: 90px"
          @change="crud.toQuery"
        >
          <el-option
            v-for="item in dict.studio_work_type"
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
          <el-form-item label="作品英文名" prop="workName">
            <el-input v-model="form.workName" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品中文名" prop="workNameC">
            <el-input v-model="form.workNameC" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品类别" prop="workType">
            <el-select v-model="form.workType" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.studio_work_type"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="作品标签">
            <el-select v-model="tmpWorkTags" multiple filterable placeholder="请选择" @change="onTagsChange">
              <el-option
                v-for="item in dict.studio_work_tag"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="作品发布年">
            <el-input v-model="form.category" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品英文描述">
            <el-input v-model="form.workDes" :rows="3" type="textarea" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品中文描述">
            <el-input v-model="form.workDesC" :rows="3" type="textarea" style="width: 340px;" />
          </el-form-item>
          <el-form-item label="作品客户">
            <el-input v-model="form.workClient" style="width: 340px;" />
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
        <el-table-column prop="workId" label="作品id" />
        <el-table-column prop="workName" label="作品英文名" />
        <el-table-column prop="workNameC" label="作品中文名" />
        <el-table-column prop="workDes" label="作品英文描述" />
        <el-table-column prop="workDesC" label="作品中文描述" />
        <el-table-column prop="workType" label="作品类别">
          <template slot-scope="scope">
            <el-tag
              type="success"
              effect="light"
            >{{ scope.row.workType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="workTags" label="作品标签" />
        <el-table-column prop="category" label="作品发布年" />
        <el-table-column prop="workClient" label="作品客户" />
        <el-table-column v-if="checkPer(['admin','studioWork:edit','studioWork:del'])" label="操作" width="150px" align="center">
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
import crudStudioWork from '@/api/studioWork'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { workId: null, workName: null, workType: null, workDes: null, workClient: null, workTags: null, category: null, createTime: null, updateTime: null, workNameC: null, workDesC: null }
export default {
  name: 'StudioWork',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['studio_work_type', 'studio_work_tag'],
  cruds() {
    return CRUD({ title: '工作室作品', url: 'api/studioWork', idField: 'workId', sort: 'workId,desc', crudMethod: { ...crudStudioWork }})
  },
  data() {
    return {
      tmpWorkTags: [],
      permission: {
        add: ['admin', 'studioWork:add'],
        edit: ['admin', 'studioWork:edit'],
        del: ['admin', 'studioWork:del']
      },
      rules: {
        workName: [
          { required: true, message: '作品英文名不能为空', trigger: 'blur' }
        ],
        workType: [
          { required: true, message: '作品类别不能为空', trigger: 'blur' }
        ],
        workNameC: [
          { required: true, message: '作品中文名不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'workName', display_name: '作品英文名' },
        { key: 'workType', display_name: '作品类别' },
        { key: 'workClient', display_name: '作品客户' },
        { key: 'workNameC', display_name: '作品中文名' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    onTagsChange(e) {
      console.log(e, this.form)
      this.form.workTags = e.reduce((a, b) => `${a}, ${b}`)
    }
  }
}
</script>

<style scoped>

</style>
