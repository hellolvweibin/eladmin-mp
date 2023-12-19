import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../layout/index'

Vue.use(Router)

export const constantRouterMap = [
  { path: '/login',
    meta: { title: '登录', noCache: true },
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/features/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/features/401'], resolve),
    hidden: true
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: (resolve) => require(['@/views/features/redirect'], resolve)
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: (resolve) => require(['@/views/home'], resolve),
        name: 'Dashboard',
        meta: { title: '首页', icon: 'index', affix: true, noCache: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'center',
        component: (resolve) => require(['@/views/system/user/center'], resolve),
        name: '个人中心',
        meta: { title: '个人中心' }
      }
    ]
  },
  {
    path: '/studio',
    component: Layout,
    redirect: '/studio/studio_base',
    name: '工作室管理',
    meta: {
      title: '工作室管理',
      icon: 'visits'
    },
    children: [
      {
        path: 'studio_work',
        component: (resolve) => require(['@/views/studio/studio_work/index'], resolve),
        name: '作品管理',
        meta: { title: '作品管理', icon: 'index' }
      },
      {
        path: 'studio_client',
        component: (resolve) => require(['@/views/studio/studio_client/index'], resolve),
        name: '客户管理',
        meta: { title: '客户管理', icon: 'wechat' }
      },
      {
        path: 'studio_partner',
        component: (resolve) => require(['@/views/studio/studio_partner/index'], resolve),
        name: '合作商管理',
        meta: { title: '合作商管理', icon: 'international' }
      },
      {
        path: 'studio_staff',
        component: (resolve) => require(['@/views/studio/studio_staff/index'], resolve),
        name: '员工管理',
        meta: { title: '员工管理', icon: 'peoples' }
      },
      {
        path: 'studio_award',
        component: (resolve) => require(['@/views/studio/studio_award/index'], resolve),
        name: '奖项管理',
        meta: { title: '奖项管理', icon: 'ipvisits' }
      }
    ]
  }

]

export default new Router({
  // mode: 'hash',
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
