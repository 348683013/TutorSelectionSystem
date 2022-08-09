// 存放选择导师过程相关

import {reqChoiceTeacher} from '@/api/index'



const state={
    // 选择的轮次
    roundId:0,
    // 当前轮次截止时间
    stopTime:'',
    // 第一轮的导师列表
    chooseList_1 : [],
    // 第二轮的导师列表
    chooseList_2 : [],
    // 已选的导师信息
    haveChooseList:[]
}
const mutations={
    GETROUNDID(state,roundId){
        state.roundId = roundId
    },
    GETSTOPTIME(state,stopTime){
        state.stopTime = stopTime
    },
    GETCHOOSELIST_1(state,chooseList_1){
        state.chooseList_1 = chooseList_1
    },
    GETCHOOSELIST_2(state,chooseList_2){
        state.chooseList_2 = chooseList_2
    },
    GETHAVECHOOSELIST(state,haveChooseList){
        state.haveChooseList = haveChooseList
    }
}
const actions={
    async getChooseList_1({commit}){
        let result = await reqChoiceTeacher()
        if(result.code===0){
            commit('GETCHOOSELIST_1',result.extend.teachers)
        }
    },
    async getChooseList_2({commit}){
        let result = await reqChoiceTeacher()
        if(result.code===0){
            commit('GETCHOOSELIST_2',result.extend.teachers)
        }
    },
}

export default {
    namespace:true,
    state,
    actions,
    mutations
}