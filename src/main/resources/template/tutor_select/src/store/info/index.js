// 存放获取到的导师信息等
import {reqTeacherList,reqTeacherListBySex} from "@/api/index"
const state={
    teacherList:[],
    totalSize:0
}
const mutations={
    GETTEACHERLIST(state,teacherList){
        state.teacherList = teacherList
    },
    GETTEACHERLISTSIZE(state,teachersInfo){
        state.totalSize = teachersInfo.total
    },
    GETTEACHERLISTBYSEX(state,teacherList){
        state.teacherList = teacherList
    }
}
const actions={
    async getTeacherList({commit},reqObj){
        let result = await reqTeacherList(reqObj)
        if(result.code===0){
            commit('GETTEACHERLIST',result.extend.teachersInfo.records)
            commit('GETTEACHERLISTSIZE',result.extend.teachersInfo)
            return 'ok'
        }else{
            return Promise.reject(new Error('fail'))
        }
    },
    async getTeacherListBySex({commit},reqObj){
        let result = await reqTeacherListBySex(reqObj)
        if(result.code===0){
            commit('GETTEACHERLISTBYSEX',result.extend.teachersInfo.records)
            commit('GETTEACHERLISTSIZE',result.extend.teachersInfo)
            return 'ok'
        }else{
            return Promise.reject(new Error('fail'))
        }
    }

}
const getters={}

export default {
    state,
    mutations,
    actions,
    getters
}