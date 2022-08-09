// 登录时收集的数据仓库
import {getLoginType} from '@/utils/loginType'

const state={
    isTeacher:getLoginType('isTeacher'),
    isStudent:getLoginType('isStudent'),
    isAdmin:getLoginType('isAdmin')
}
const mutations={
    CHANGEISTEACHER(state,isTeacher){
        state.isTeacher = isTeacher
    },
    CHANGEISSTUDENT(state,isStudent){
        state.isStudent = isStudent
    },
    CHANGEISADMIN(state,isAdmin){
        state.isAdmin = isAdmin
    }
}
const actions={
    getIsTeacher({commit},isTeacher){
        commit('CHANGEISTEACHER',isTeacher)
    },
    getIsStudent({commit},isStudent){
        commit('CHANGEISSTUDENT',isStudent)
    },
    getIsAdmin({commit},isAdmin){
        commit('CHANGEISADMIN',isAdmin)
    },
    initLoginType({commit}){
        commit('CHANGEISTEACHER',false)
        commit('CHANGEISSTUDENT',false)
        commit('CHANGEISADMIN',false)
    }
}
const getters={}

export default {
    state,
    mutations,
    actions,
    getters
}