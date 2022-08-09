// 用户信息
import {getToken,setToken,clearToken} from '@/utils/token'

import { reqStudentLogin,reqStudentInfo,reqTeacherLogin} from '@/api'


const state={
    token:getToken(),
    userInfo:{}
}
const mutations={
    USERLOGIN(state,token){
        state.token = token
    },
    GETUSERINFO(state,userInfo){
        state.userInfo = userInfo
    },
    USERLOGOUT(state){
        state.token=''
        state.userInfo={}
        clearToken()
    }
}
const actions={
    async studentLogin({commit},data){
        let result = await reqStudentLogin(data)
        if(result.code===0){
            // 发送登录的请求,并得到成功的响应
            commit('USERLOGIN',result.extend.studentInfo.token)
            // 将得到的token存入本地存储
            setToken(result.extend.studentInfo.token) 
        }
    },

    async getStudentInfo({commit}){
        let result = await reqStudentInfo()
        if(result.code===0){
            // console.log(result);
            commit('GETUSERINFO',result.extend.studentInfo)
            commit('GETROUNDID',result.extend.roundInfo.roundId,{root:true})
            commit('GETSTOPTIME',result.extend.roundInfo.stopTime,{root:true})
            commit('GETHAVECHOOSELIST',result.extend.teacherIds)
        }
    },

    userLogout({commit}){
        commit('USERLOGOUT')
    },

    async teacherLogin({commit},data){
        let result = await reqTeacherLogin(data)
        if(result.code===0){
            // 发送登录的请求,并得到成功的响应
            commit('USERLOGIN',result.extend.teacherInfo.token)
            // 将得到的token存入本地存储
            setToken(result.extend.teacherInfo.token) 
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