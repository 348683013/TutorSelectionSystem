<template>
  <div class="pick-wrap">
    <div class="title">
      <h1 v-show="!isTeacher">一志愿选择将于xxxxxxx后结束,请尽快选择！</h1>
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
        @change="handleChange"
        :data="data"
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
import { mapState } from "vuex";
export default {
  name: "Pick",
  data() {
    const generateData = (_) => {
      const data = [];
      for (let i = 1; i <= 15; i++) {
        data.push({
          key: i,
          label: `备选项 ${i}`,
        });
      }
      return data;
    };
    return {
      data: generateData(),
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
        selectEvent: function () {
          alert("选中的导师");
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
      isStudent:state=>state.login.isStudent,
      isTeacher:state=>state.login.isTeacher
    })
  },
  methods: {
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys);
    },
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