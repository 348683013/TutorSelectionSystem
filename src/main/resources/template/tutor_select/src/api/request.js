import axios from 'axios'
import store from '@/store'

const request = axios.create({
    baseURL:'',
    timeout:2000
})

request.interceptors.request.use((config)=>{
    if(store.state.user.token){
        config.headers.token = store.state.user.token
    }
})

request.interceptors.response.use((res)=>{
    return res.data
},(error)=>{
    return new Promise.reject(new Error('fail'))
})

export default request