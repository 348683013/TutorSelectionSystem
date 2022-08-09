import {request,teacherRequest} from './request'

//在这里写请求
export const reqStudentLogin = (data)=>request({
    // url:`/StudentLogin?accountNumber=${data.accountNumber}&password=${data.password}`,
    url:`/StudentLogin`,
    method:'post',
    data:data
})

export const reqStudentInfo = ()=>request({
    url:'/findStudentByToken',
    method:'post'
})

export const reqTeacherList = (data)=>request({
    url:'/ShowTeacher/findAllTeachers',
    method:'post',
    data:data
})

export const reqTeacherListBySex = (data)=>request({
    url:'/ShowTeacher/findAllTeachersBySex',
    method:'post',
    data
})

export const reqChoiceTeacher = ()=>request({
    url:'/ChoiceTeacher/hasSpareTeachers',
    method:'get'
})

export const reqSendChoice = (data)=>request({
    url:'/ChoiceTeacher/sendRequest',
    method:'post',
    data
})

export const reqTeacherLogin = (data)=>teacherRequest({
    url:'/TeacherLogin',
    method:'post',
    data
})