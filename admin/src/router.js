import Vue from 'vue'
import Router from 'vue-router'
import Login from './view/login.vue'
import Admin from './view/admin.vue'
import Welcome from './view/admin/welcome.vue'
import Category from './view/admin/category.vue'
import Course from './view/admin/course.vue'
import Chapter from './view/admin/chapter.vue'
import Section from './view/admin/section.vue'

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
            path: '/login',
            component: Login
        },
        {
            path: '/',
            name: 'admin',
            component: Admin,
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
            ]
        }
    ]
})