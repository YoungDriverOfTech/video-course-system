import Vue from 'vue'
import Router from 'vue-router'
import Login from './view/login.vue'
import Admin from './view/admin.vue'
import Welcome from './view/admin/welcome.vue'
import Category from './view/admin/category.vue'
import Course from './view/admin/course.vue'
import Chapter from './view/admin/chapter.vue'
import Section from './view/admin/section.vue'
import Content from "./view/admin/content.vue"
import Teacher from './view/admin/teacher.vue'
import File from './view/admin/file.vue'
import User from './view/admin/user.vue'
import Resource from './view/admin/resource.vue'
import Role from './view/admin/role.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '*',
            redirect: '/login'
        },
        {
            path: '',
            redirect: '/login'
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/',
            name: 'admin',
            component: Admin,
            meta: {
                loginRequire: true
            },
            children: [
                {
                    path: 'welcome',
                    name: 'welcome',
                    component: Welcome
                },
                {
                    path: 'business/course',
                    name: 'business/course',
                    component: Course
                },
                {
                    path: 'business/chapter',
                    name: 'business/chapter',
                    component: Chapter
                },
                {
                    path: 'business/section',
                    name: 'business/section',
                    component: Section
                },
                {
                    path: 'business/category',
                    name: 'business/category',
                    component: Category
                },
                {
                    path: 'business/content',
                    name: 'business/content',
                    component: Content
                },
                {
                    path: 'business/teacher',
                    name: 'business/teacher',
                    component: Teacher
                },
                {
                    path: 'file/file',
                    name: 'file/file',
                    component: File
                },
                {
                    path: 'system/user',
                    name: 'system/user',
                    component: User
                },
                {
                    path: 'system/resource',
                    name: 'system/resource',
                    component: Resource
                },
                {
                    path: 'system/role',
                    name: 'system/role',
                    component: Role
                },
            ]
        }
    ]
})