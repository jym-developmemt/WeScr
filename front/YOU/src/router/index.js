import Vue from 'vue'
import VueRouter from 'vue-router'
import MainPage from '../views/MainPage.vue'
import LoginPage from '../views/LoginPage.vue'
import NotFoundPage from '../views/404.vue'

Vue.use(VueRouter)

const routes = [
  // 默认页面
  {
    path: '/',
    component: LoginPage,
  },
  // 登录页面
  {
    path: '/login',
    component: LoginPage,
  },
  // 登录页面
  {
    path: '/login/:group_id/:page_id',
    component: LoginPage,
  },

  // 默认页面(分站点)
  {
    path: '/:site_id',
    component: MainPage,
  },
  // 登录页面(分站点)
  {
    path: '/login/:site_id',
    component: LoginPage,
  },
  // 登录页面(分站点)
  {
    path: '/login/:site_id/:group_id/:page_id',
    component: LoginPage,
  },

  // 主页面
  {
    path: '/:group_id/:page_id',
    component: MainPage,
  },
  // 主页面(分站点)
  {
    path: '/:site_id/:group_id/:page_id',
    component: MainPage,
  },

  // 非法请求
  {
    path: '*',
    component: NotFoundPage
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
