export const getChooseNO = ()=>{
    return localStorage.getItem('CHOOSENO')
}
export const setChooseNO = (value)=>{
    localStorage.setItem('CHOOSENO',value) 
}

export const clearChooseNO = ()=>{
    localStorage.removeItem('CHOOSENO')
}