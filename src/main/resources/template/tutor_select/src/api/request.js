import axios from 'axios'
import store from '@/store'

export const request = axios.create({
    baseURL:'http://10.16.88.144:8080/Student',
    timeout:2000
})

export const teacherRequest = axios.create({
    baseURL:'http://10.16.88.144:8080/Teacher',
    timeout:2000
})

request.interceptors.request.use((config)=>{
    if(store.state.user.token){
        config.headers.token = store.state.user.token
    }

    return config
})

request.interceptors.response.use((res)=>{
    return res.data
},(error)=>{
    return Promise.reject(new Error('fail'))
})

teacherRequest.interceptors.request.use((config)=>{
    if(store.state.user.token){
        config.headers.token = store.state.user.token
    }

    return config
})

teacherRequest.interceptors.response.use((res)=>{
    return res.data
},(error)=>{
    return Promise.reject(new Error('fail'))
})
