import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import login from './login'
import user from './user'



export default new Vuex.Store({
    modules:{
        login,
        user
    }
})