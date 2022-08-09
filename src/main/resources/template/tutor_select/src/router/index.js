import Vue from 'vue'
import VueRouter from 'vue-router'

import store from '@/store/index'

import { getLoginType } from '@/utils/loginType'

Vue.use(VueRouter)

import routes from './routes'

let originPush = VueRouter.prototype.push
VueRouter.prototype.push = function (location, resolve, reject) {
    if (resolve && reject) {
        originPush.call(this, location, resolve, reject)
    } else {
        originPush.call(this, location).catch(err => err)
    }
}

let originReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function (location, resolve, reject) {
    if (resolve && reject) {
        originReplace.call(this, location, resolve, reject)
    } else {
        originReplace.call(this, location, () => { }, () => { }).catch(err => err)
    }
}

let router = new VueRouter({
    routes
})

// 全局路由守卫,控制各登陆种类不能进入别的登陆身份的页面,登录的状态下不能进入登陆页面
router.beforeEach(async (to, from, next) => {
    let token = store.state.user.token
    let realname = store.state.user.userInfo.realname
    if (token) {
        if (to.path === '/login') {
            if (getLoginType('isStudent')) {
                next('/home')
            }
            if (getLoginType('isTeacher')) {
                next('/apply')
            }
            if (getLoginType('isAdmin')) {
                next('/student')
            }
        } else {
            if (realname) {
                if (to.path === '/pick1') {
                    if (state.user.userInfo.hasTutor !== '0') {
                        next('/user')
                    } else if (state.choose.roundId !== "1") {
                        next('/pick2')
                    }
                }
                next()
            } else {
                // 此情况是刷新页面的情况,store中的数据都会被初始化,因此需要重新获取信息
                try {
                    if (getLoginType('isStudent')) {
                        await store.dispatch('getStudentInfo')
                        next()
                    }
                    // if(getLoginType('isTeacher')){
                    //     await store.dispatch('getStudentInfo')
                    //     next()
                    // }
                    // if(getLoginType('isAdmin')){
                    //     await store.dispatch('getStudentInfo')
                    //     next()
                    // }

                } catch (error) {
                    // token过期的情况
                    // 清除token及用户信息
                    await store.dispatch('userLogout')
                    next('/login')
                }

            }

        }
    } else {
        let toPath = to.path
        if (toPath !== '/login') {
            next('/login')
        } else {
            next()
        }
        // next()
    }
})

export default router