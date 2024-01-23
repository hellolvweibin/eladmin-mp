<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">奖项名称</label>
        <el-input v-model="query.awardName" clearable placeholder="奖项名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">奖项年份</label>
        <el-input v-model="query.awardYear" clearable placeholder="奖项年份" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">奖项类别</label>
        <el-select
          v-model="query.awardTag"
          clearable
          size="small"
          placeholder="类别"
          class="filter-item"
          style="width: 90px"
          @change="crud.toQuery"
        >
          <el-option
            v-for="item in dict.studio_award_tag"
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
          <el-form-item label="奖项名称" prop="awardName">
            <el-input v-model="form.awardName" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="奖项年份" prop="awardYear">
            <el-input v-model="form.awardYear" style="width: 187px;" />
          </el-form-item>
          <el-form-item label="奖项类别" prop="awardTag">
            <el-select v-model="form.awardTag" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.studio_award_tag"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
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
        <el-table-column prop="partnerTag" label="奖项类别">
          <template slot-scope="scope">
            <el-tag
              type="success"
              effect="light"
            >{{ scope.row.awardTag }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="partnerTag" label="获奖年份">
          <template slot-scope="scope">
            <el-tag
              type="danger"
              effect="light"
            >{{ scope.row.awardYear }}</el-tag>
          </template>
        </el-table-column>
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
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { awardId: null, awardName: null, createTime: null, updateTime: null, awardTag: null, awardYear: null }
export default {
  name: 'StudioAward',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['studio_award_tag'],
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
        awardName: [
          { required: true, message: '奖项名称不能为空', trigger: 'blur' }
        ],
        awardTag: [
          { required: true, message: '奖项分类不能为空', trigger: 'blur' }
        ],
        awardYear: [
          { required: true, message: '奖项年份不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'awardName', display_name: '奖项名称' },
        { key: 'awardTag', display_name: '奖项分类' },
        { key: 'awardYear', display_name: '奖项年份' }
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
