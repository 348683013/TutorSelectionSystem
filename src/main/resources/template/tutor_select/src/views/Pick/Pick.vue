<template>
  <div class="pick-wrap">
    <div class="title">
      <h1 v-show="!isTeacher">第{{roundId===1?'一':'二'}}轮志愿选择将于{{ stopTime }}结束,请尽快选择！</h1>
      <h1 v-show="isTeacher">您的剩余名额为:</h1>
    </div>
    <div class="pick">
      <el-transfer
        style="text-align: left; display: inline-block; height: 600px"
        v-model="value"
        filterable
        :left-default-checked="[]"
        :right-default-checked="[]"
        :render-content="renderFunc"
        :titles="[attr.apply, attr.readyApply]"
        :button-texts="['取消备选', '作为备选']"
        :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}',
        }"
        @right-check-change="handleChange"
        :data="dataShow"
      >
        <el-button
          class="transfer-footer"
          slot="left-footer"
          size="small"
          @click="attr.goAllApplyEvent"
          >{{ attr.goAllApply }}</el-button
        >
        <el-button
          class="transfer-footer"
          slot="right-footer"
          size="small"
          @click="attr.selectEvent"
          >{{ attr.select }}</el-button
        >
      </el-transfer>
    </div>
  </div>
</template>

<script>
import { reqSendChoice } from "@/api";
import { mapState } from "vuex";
export default {
  name: "Pick",
  data() {
    return {
      checkId: [],
      data1: [],
      value: [],
      value4: [1],
      renderFunc(h, option) {
        return (
          <span>
            {option.key} - {option.label}
          </span>
        );
      },
      teacherAttr: {
        apply: "所有申请",
        readyApply: "备选申请",
        goAllApply: "查看申请",
        goAllApplyEvent: () => {
          this.$router.push("/apply");
        },
        select: "选择选中的学生",
        selectEvent: function () {
          alert("选中的学生");
        },
      },
      studentAttr: {
        apply: "可选导师",
        readyApply: "备选导师",
        goAllApply: "进入导师预览",
        goAllApplyEvent: () => {
          this.$router.push("/list");
        },
        select: "选择选中的导师",
        selectEvent: () => {
          if (this.checkId.length === 0) {
            alert("请选择导师之后提交");
          } else if (this.checkId.length > 5 - this.haveChooseList.length) {
            alert(
              `超过总可发送导师数,您的剩余可发送导师数为${
                5 - this.haveChooseList.length
              }`
            );
          } else {
            if (confirm("确认提交?")) {
              reqSendChoice({
                checkId: this.checkId,
                roundId: this.roundId,
              }).then(resolve=>{
                alert('提交成功!')
                setTimeout(()=>{
                  location.reload()
                })
              },1000);
              
            }
          }
        },
      },
    };
  },

  computed: {
    attr() {
      let attr = {};
      if (this.isTeacher) {
        attr = this.teacherAttr;
      }
      if (this.isStudent) {
        attr = this.studentAttr;
      }
      return attr;
    },
    ...mapState({
      isStudent: (state) => state.login.isStudent,
      isTeacher: (state) => state.login.isTeacher,
      roundId: (state) => state.choose.roundId,
      stopTime: (state) => state.choose.stopTime,
      haveChooseList: (state) => state.choose.haveChooseList,
    }),
    dataShow() {
      let processData = [];
      if (this.haveChooseList.length === 5) {
        this.data1.forEach((item) => {
          processData.push({
            ...item,
            disabled: true,
          });
        });
        return processData;
      } else {
        this.data1.forEach((item) => {
          if (this.haveChooseList.indexOf(item.key)!==-1) {
            processData.push({
              ...item,
              disabled: true,
            });
          } else {
            processData.push(item);
          }
        });
        return processData;
      }
    },
  },
  methods: {
    handleChange(checkId) {
      this.checkId = checkId;
    },
  },
  async mounted() {
    // console.log(this.data);
    if (this.roundId === 1) {
      await this.$store.dispatch("getChooseList_1");
      this.$store.state.choose.chooseList_1.forEach((item) => {
        this.data1.push({
          key: item.teacherId,
          label: item.realname,
        });
      });
    } else {
      await this.$store.dispatch("getChooseList_2");
      this.$store.state.choose.chooseList_2.forEach((item) => {
        this.data1.push({
          key: item.teacherId,
          label: item.realname,
        });
      });
    }
  },
};
</script>

<style scoped>
.transfer-footer {
  margin-left: 20px;
  padding: 6px 5px;
}
.pick-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.title {
  align-self: flex-start;
  margin-left: 30px;
  font-size: 30px;
}
.pick {
  margin-top: -60px;
}
</style>