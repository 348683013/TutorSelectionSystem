<template>
  <el-table
    style="width: 100%"
    :data="
      tableData.filter(
        (data) =>
          !search ||
          data.name.toLowerCase().includes(search.toLowerCase()) ||
          data.number.toLowerCase().includes(search.toLowerCase())
      )
    "
  >
    <el-table-column fixed prop="name" :label="attr.name" width="150">
    </el-table-column>
    <el-table-column prop="number" :label="attr.number" width="150"> </el-table-column>
    <el-table-column prop="class" :label="attr.class" width="150"> </el-table-column>
    <el-table-column prop="phone" :label="attr.phone" width="150"> </el-table-column>
    <el-table-column prop="email" :label="attr.email" width="150"> </el-table-column>
    <el-table-column prop="email" :label="attr.status" width="150">
    </el-table-column>
    <el-table-column prop="address" width="400">
      <template slot="header" slot-scope="scope">
        {{attr.address}}
        <el-button type="primary" size="mini" class="add-btn" @click="attr.addEvent"
          >{{attr.add}}</el-button
        >
      </template>
    </el-table-column>
    <el-table-column fixed="right" width="150">
      <template slot="header" slot-scope="scope">
        <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
      </template>
      <template slot-scope="scope">
        <el-button size="mini" @click="handleEdit(scope.$index, scope.row,attr.edit)"
          >编辑</el-button
        >
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "Manage",
  props: ["attr", "tableData"],
  data() {
    return {
      search: "",
    };
  },
  methods: {
    handleEdit(index, row,edit) {
      // console.log(index, row);
      edit(row)
    },
    handleDelete(index, row) {
      // console.log(index, row);
    },
  },
};
</script>

<style scoped>
.add-btn{
    margin-left: 60%;
}
</style>