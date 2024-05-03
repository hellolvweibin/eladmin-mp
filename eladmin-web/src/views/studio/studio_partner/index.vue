<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">合作商英文名</label>
        <el-input v-model="query.partnerName" clearable placeholder="合作商英文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">合作商中文名</label>
        <el-input v-model="query.partnerNameC" clearable placeholder="合作商中文名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">合作商标签</label>
        <el-select
          v-model="query.partnerTag"
          clearable
          size="small"
          placeholder="类别"
          class="filter-item"
          style="width: 90px"
          @change="crud.toQuery"
        >
          <el-option
            v-for="item in dict.studio_partner_tag"
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
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
          <el-form-item label="合作商英文名" prop="partnerName">
            <el-input v-model="form.partnerName" style="width: 320px;" />
          </el-form-item>
          <el-form-item label="合作商中文名" prop="partnerNameC">
            <el-input v-model="form.partnerNameC" style="width: 320px;" />
          </el-form-item>
          <el-form-item label="合作商标签">
            <el-select v-model="form.partnerTag" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.studio_partner_tag"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="合作商手机号">
            <el-input v-model="form.partnerPhone" style="width: 320px;" />
          </el-form-item>
          <el-form-item label="合作商邮箱">
            <el-input v-model="form.partnerMail" style="width: 320px;" />
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
        <el-table-column prop="partnerId" label="合作商id" />
        <el-table-column prop="partnerName" label="合作商英文名" />
        <el-table-column prop="partnerNameC" label="合作商中文名" />
        <el-table-column prop="partnerTag" label="合作商标签">
          <template slot-scope="scope">
            <el-tag
              type="success"
              effect="light"
            >{{ scope.row.partnerTag }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="partnerPhone" label="合作商手机号" />
        <el-table-column prop="partnerMail" label="合作商邮箱" />
        <el-table-column v-if="checkPer(['admin','studioPartner:edit','studioPartner:del'])" label="操作" width="150px" align="center">
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
import crudStudioPartner from '@/api/studioPartner'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { partnerId: null, partnerName: null, partnerNameC: null, partnerTag: null, partnerPhone: null, partnerMail: null, createTime: null, updateTime: null }
export default {
  name: 'StudioPartner',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['studio_partner_tag'],
  cruds() {
    return CRUD({ title: '工作室合作商', url: 'api/studioPartner', idField: 'partnerId', sort: 'partnerId,desc', crudMethod: { ...crudStudioPartner }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'studioPartner:add'],
        edit: ['admin', 'studioPartner:edit'],
        del: ['admin', 'studioPartner:del']
      },
      rules: {
        partnerName: [
          { required: true, message: '合作商英文名不能为空', trigger: 'blur' }
        ],
        partnerNameC: [
          { required: true, message: '合作商中文名不能为空', trigger: 'blur' }
        ],
        partnerTag: [
          { required: true, message: '合作商标签不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'partnerName', display_name: '合作商英文名' },
        { key: 'partnerNameC', display_name: '合作商中文名' },
        { key: 'partnerTag', display_name: '合作商标签' }
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
