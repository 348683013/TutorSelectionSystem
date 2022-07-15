// 用户信息
import {getToken,setToken,clearToken} from '@/utils/token'

import { reqLogin } from '@/api'


const state={
    token:getToken(),
    userInfo:{}
}
const mutations={
    USERLOGIN(state,token){
        state.token = token
    },
    USERLOGOUT(state){
        state.token=''
        state.userInfo={}
        clearToken()
    }
}
const actions={
    async userLogin({commit},data){
        let result = await reqLogin(data)
        // 发送登录的请求,并得到成功的响应
        commit('USERLOGIN',token)
        // 将得到的token存入本地存储
        setToken(token)
    },

    userLogout({commit}){
        commit('USERLOGOUT')
    }

}
const getters={}

export default {
    state,
    mutations,
    actions,
    getters
}