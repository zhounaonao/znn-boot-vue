import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import {ElMessage} from "element-plus";
import {getToken} from "@/utils/auth";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/index", // 重定向
    children: [
      {
        path: '/index',
        name: 'Index',
        component: () => import("@/views/Index")
      },
      {
        path: '/user',
        name: 'User',
        component: () => import("@/views/User")
      },
      {
        path: '/book',
        name: 'Book',
        component: () => import("@/views/Book")
      },
      {
        path: '/person',
        name: 'Person',
        component: () => import("@/views/Person")
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  },
  {
    path: '/401',
    name: '401',
    component: () => import("@/views/errorPage/401")
  },
  {
    path: '/403',
    name: '403',
    component: () => import("@/views/errorPage/403")
  },
  {
    path: '/404',
    name: '404',
    component: () => import("@/views/errorPage/404")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) =>{
  if (to.path === '/login' || to.path === '/register') {
    // 登录或者注册才可以往下进行
    next();
  } else {
    // 获取 token
    let token = getToken()
    console.log(token);
    console.log("cc")
    // token 不存在
    if (!token || token === '' ) {
      console.log("aa")
      ElMessage.error('您还没有登录，请先登录');
      next('/login');
    } else {
      console.log("bb")
      next();
    }
  }
})

export default router
