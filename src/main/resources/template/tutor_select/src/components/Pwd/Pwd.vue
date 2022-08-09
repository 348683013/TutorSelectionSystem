<template>
  <div class="bread-crumb-wrap">
    <el-breadcrumb separator-class="el-icon-arrow-right" class="mid">
      <el-breadcrumb-item :to="{ path: '/home' }" v-show="isStudent"
        ><span class="bread-crumb">首页</span>
      </el-breadcrumb-item>
      <el-breadcrumb-item
        ><span class="bread-crumb">{{ pwdName }}</span>
      </el-breadcrumb-item>
      <!-- <el-breadcrumb-item><span class="bread-crumb">活动列表</span> </el-breadcrumb-item> -->
      <!-- <el-breadcrumb-item><span class="bread-crumb">活动详情</span> </el-breadcrumb-item> -->
    </el-breadcrumb>
    <div class="min-userinfo">
      <div class="welcome">欢迎您：{{realname}}</div>
      <div class="term">{{currentYear}}-{{currentYear+1}}第一学期</div>
      <div class="now-time">{{now}}</div>
      <div class="logout" @click="logOut">退出</div>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {clearLoginType} from '@/utils/loginType'
export default {
  name: "Pwd",
  data() {
    return {
      now:'',
      currentYear:''
    };
  },
  mounted() {
    this.getTime();
  },
  computed:{
    pwdName(){
      switch (this.$route.path) {
        case "/list":
          return '预览导师'
          
        case "/process":
          return '选取流程'
          
        case "/user":
          return '个人信息'
          
        case "/pick1":
          return '一志愿选择'
          
        case "/pick2":
          return '二志愿选择'
        
        case "/apply":
          return '查看申请'

        case "/student":
          return '学生管理'

        case "/teacher":
          return '导师管理'

        case "/admin":
          return '其他事务'

        case "/condition":
          return '学生选导师情况'
      }
    },
    ...mapState({
      isStudent:state=>state.login.isStudent,
      realname:state=>state.user.userInfo.realname
    })
  },
  methods: {
    getTime() {
      setInterval(() => {
        let now = new Date();
        let h = now.getHours();
        h = h < 10 ? "0" + h : h;
        let m = now.getMinutes();
        m = m < 10 ? "0" + m : m;
        let s = now.getSeconds();
        s = s < 10 ? "0" + s : s;
        let y = now.getFullYear()
        this.currentYear = y
        this.now = `${h}:${m}:${s}`
      }, 1000); 
    },
    logOut(){
      this.$store.dispatch('userLogout')
      clearLoginType()
      this.$router.push('/login')
      this.$store.dispatch('initLoginType')
      // sessionStorage.setItem('isStudent',false)
      // sessionStorage.setItem('isTeacher',false)
      // sessionStorage.setItem('isAdmin',false)


    }
  },
};
</script>

<style>
.bread-crumb-wrap {
  height: 30px;
  background-color: #225522;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.mid {
  line-height: 30px;
  margin-left: 10px;
}
.bread-crumb {
  color: white;
}
.min-userinfo {
  color: white;
  display: flex;
  justify-content: space-around;
  width: 350px;
  font-size: 14px;
}
</style>