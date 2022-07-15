<template>
  <div class="teacher-list-wrapper">
    <div class="teacher-list-wrap">
      <div class="teacher-list">
        <div>
          <el-table
            style="width: 100%"
            :data="
              tableData.filter(
                (data) =>
                  !search ||
                  data.name.toLowerCase().includes(search.toLowerCase())
              )
            "
          >
            <el-table-column fixed prop="name" label="导师" width="150">
            </el-table-column>
            <el-table-column label="学生一" width="300">
              <el-table-column prop="member1.name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="member1.number" label="学号" width="150">
              </el-table-column>
            </el-table-column>
            <el-table-column label="学生二" width="300">
              <el-table-column prop="member2.name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="member2.number" label="学号" width="150">
              </el-table-column>
            </el-table-column>
            <el-table-column label="学生三" width="300">
              <el-table-column prop="member3.name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="member3.number" label="学号" width="150">
              </el-table-column>
            </el-table-column>
            <el-table-column label="学生四" width="300">
              <el-table-column prop="member4.name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="member4.number" label="学号" width="150">
              </el-table-column>
            </el-table-column>
            <el-table-column label="学生五" width="300">
              <el-table-column prop="member5.name" label="姓名" width="100">
              </el-table-column>
              <el-table-column prop="member5.number" label="学号" width="130">
              </el-table-column>
            </el-table-column>

            <el-table-column fixed="right" width="150">
              <template slot="header" slot-scope="scope">
                <el-input
                  v-model="search"
                  size="mini"
                  placeholder="输入关键字搜索"
                />
              </template>
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)"
                  >编辑</el-button
                >
                <el-button size="mini" type="danger">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <transition name="el-fade-in">
        <router-view></router-view>
      </transition>
      <Paganation />
    </div>
  </div>
</template>

<script>
export default {
  name: "Condition",
  data() {
    return {
      tableData: [
        {
          name: "张鑫南",
          member1: {
            name: "张三",
            number: 2016212304,
          },
          member2: {
            name: "孙三",
            number: 2016212305,
          },
          member3: {
            name: "李三",
            number: 2016212306,
          },
          member4: {
            name: "钱三",
            number: 2016212307,
          },
          member5: {
            name: "胡三",
            number: 2016212308,
          },
        },
      ],
      search: "",
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
      this.$router.push({ path: "/condition/distribution" ,query:{data:row}});
    },
  },
};
</script>

<style scoped>
.teacher-list-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.teacher-list-wrap {
  width: 90%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.teacher-list {
  width: 100%;
  height: 92%;
}
</style>