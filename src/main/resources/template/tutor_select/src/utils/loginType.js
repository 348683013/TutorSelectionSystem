export const getLoginType = (value)=>{
    let type = localStorage.getItem('LOGINTYPE')
    if(value===type){
        return true
    }else{
        return false
    }
}
export const setLoginType = (value)=>{
    localStorage.setItem('LOGINTYPE',value) 
}

export const clearLoginType = ()=>{
    localStorage.removeItem('LOGINTYPE')
}