<template>
  <div id="mask">
    <div class="teacher-detail-wrapper">
      <div class="back" @click="goBack">
        <i class="el-icon-close"></i>
      </div>
      <div class="teacher-detail-wrap">
        <div class="teacher-photo">
          <img :src="info.headImage"  style="width:100%;height:100%" alt="">
        </div>
        <div class="teacher-detail-info">
          <el-descriptions class="margin-top" :column="3" :size="size" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                姓名
              </template>
              {{info.realname}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                手机号
              </template>
              {{info.telephone}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                职称
              </template>
              {{info.teacherType}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                备注
              </template>
              <el-tag size="small">名额未满</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                联系地址
              </template>
              {{info.address}}
            </el-descriptions-item>
          </el-descriptions>
          <!-- <el-input
            type="textarea"
            :rows="6"
            :placeholder="result"
            v-model="result"
            disabled
            style="color:black;"
          >
          </el-input> -->
          <div class="result">
            <div style="width:90%;height:90%;">
              <div>履历</div>
              <div class="result-content">{{info.description}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
export default {
  name: "Detail",
  data() {
    return {
      result:
        "12222222222222222222222222222221111111111111111111111111111111111111111111111111111111111111111111111111111111111111111133311111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
      size: "",
    };
  },
  computed:{
    ...mapState({
      teacherList:state=>state.info.teacherList
    }),
    info(){
      return this.teacherList[this.$route.query.index]
    }
  },
  mounted(){
    console.log(this.info);
  },
  methods: {
    goBack() {
      this.$router.push("/list");
    },
  },
};
</script>

<style scoped>
#mask {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.teacher-detail-wrapper {
  width: 50%;
  height: 50%;
  background-color: white;
  border-radius: 10px;
  position: relative;
}
.teacher-detail-wrap {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.back {
  position: absolute;
  top: 5%;
  right: 3%;
  font-size: 24px;
}
.back:hover{
    color:rgb(64,158,255)
}
.teacher-photo {
  width: 18%;
  height: 50%;
  border: 1px rgb(235, 238, 245) solid;
}
.teacher-detail-info {
  height: 50%;
  width: 60%;
    

}
.result {
  height: 59%;
  width: 100%;
  border: 1px solid rgb(235, 238, 245);
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow-y: scroll;
}
.result-content{
     word-wrap: break-word;
    word-break: normal;
    margin-top:1%;
    width: 100%;
    height: 80%;

}
</style>