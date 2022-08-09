<template>
  <div class="list-page">
    <div class="search-wrapper">
        <Search @getSearchData="getSearchData" @getSexData="getSexData"/>
    </div>
    <div class="list-wrapper">
        <Teacher :teacherList="teacherList"/>
    </div>
    <div class="paganation">
      <Paganation :totalSize = "totalSize" @getCurrentPage="getCurrentPage"/>
    </div>
    <transition name="el-fade-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import Teacher from './Teacher/Teacher.vue'
export default {
  name: "List",
  data() {
    return {
      nameKeyWord:'',
      page:1,
      sexKeyWord:''
    }
  },
  components:{
    Teacher
  },
  async mounted(){
    await this.$store.dispatch('getTeacherList',this.reqObj)
  },
  computed:{
    ...mapState({
      teacherList:state=>state.info.teacherList,
      totalSize:state=>state.info.totalSize
    }),
    reqObj(){
      return {
        nameKeyWord:this.nameKeyWord,
        page:this.page
      }
    },
    reqObjBySex(){
      return{
        page:this.page,
        sexKeyWord:this.sexKeyWord
      }
    }
  },
  methods:{
    async getSearchData(nameKeyWord){
      this.nameKeyWord = nameKeyWord
      await this.$store.dispatch('getTeacherList',this.reqObj)
    },
    async getCurrentPage(currentPage){
      this.page = currentPage
      if(this.sexKeyWord===''){
        await this.$store.dispatch('getTeacherList',this.reqObj)
      }else{
        await this.$store.dispatch('getTeacherListBySex',this.reqObjBySex)
      }
    },
    async getSexData(sexKeyWord){
      this.sexKeyWord = sexKeyWord
      await this.$store.dispatch('getTeacherListBySex',this.reqObjBySex)
    }
  }
};
</script>

<style scoped>
    .list-page{
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .search-wrapper{
        width: 500px;
        margin-top: 1   0px;
    }
    .list-wrapper{
        margin-top: 20px;
    }
    .paganation{
        margin-top: 35px;
    }
</style>