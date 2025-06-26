<style lang="scss" scoped>
@import '@/styles/student';

.head {
  width: 100%;
  max-width: 600px;
  display: flex;
  margin: 0 auto 20px;
  gap: 15px;

  .el-input {
    flex: 1;
  }
}

.el-table {
  @include card-style;
  margin-bottom: 20px;

  :deep(.el-table__header) th {
    background-color: lighten($primary-color, 40%);
    color: $text-primary;
    font-weight: bold;
  }

  :deep(.el-table__row) {
    transition: $transition-all;

    &:hover {
      background-color: rgba($primary-color, 0.05);
    }
  }

  .el-button {
    transition: $transition-all;

    &:hover {
      transform: translateY(-2px);
    }
  }
}

.pager {
  @include card-style;
  padding: 12px;

  .slot {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;

    .total .value {
      color: $primary-color;
      font-weight: bold;
    }

    .jumper .input {
      .el-input {
        width: 60px;
      }
    }
  }
}

@media (max-width: 768px) {
  .head {
    width: 90%;
    flex-direction: column;

    .el-input {
      width: 100%;
      margin-right: 0;
      margin-bottom: 10px;
    }
  }

  .el-table {
    :deep(.el-table-column) {
      &:nth-child(n+3) {
        display: none;
      }
    }
  }
}
</style>
<template>
        <div class="head">
                <el-input type="text" v-model="keyword" style="margin-right:15px;"> </el-input>
                        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        </div>
        <el-table :data="tableDataPagedList" :scrollbar-always-on="true" style="width: 100%">
                <el-table-column prop="id" label="ID" width="100px" />
                <el-table-column prop="name" label="实验名" width="200px" />
                <el-table-column prop="description" label="实验说明" width="858px" />
                <el-table-column label="操作" fixed="right" width="200px">
                        <template #default="scope">
                                <el-button type="primary" :icon="DArrowRight" @click="toDetail(scope.row.id)" style="width: 180px;">查看作业详情</el-button>
                        </template>
                </el-table-column>
        </el-table>
        <div class="pager">
                <el-pagination v-model:current-page="currentPage" v-model:page-size="currentSize"
                        layout=" prev, pager, next,slot" @current-change="handlePaginationChange" :total="total">
                        <template #default>
                                <div class="slot">
                                        <div class="sizes">
                                                <el-select v-model="currentSize" placeholder="页数" size="normal"
                                                        @change="handlePaginationChange">
                                                        <el-option v-for="size in pageSizes" :key="size"
                                                                :label="size + '/页'" :value="size" />
                                                </el-select>
                                        </div>
                                        <div class="total">
                                                <div class="label">
                                                        总数 :
                                                </div>
                                                <div class="value">
                                                        {{ total }}
                                                </div>
                                        </div>
                                        <div class="jumper">
                                                <div class="label">
                                                        跳转至
                                                </div>
                                                <div class="input">
                                                        <el-input v-model.number="currentPage"
                                                                @change="handlePaginationChange" style="width: 50px;" />
                                                </div>
                                        </div>
                                </div>
                        </template>
                </el-pagination>
        </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { listItemByStudentId as listItem } from "@api/experiment";
import { ElMessage } from "element-plus";
import { Search, DArrowRight } from "@element-plus/icons-vue"
import { getUserInfo } from "@utils/access"
import { computed } from "vue"
import { useRouter } from "vue-router"
const router=useRouter()

const studentInfo = getUserInfo()

const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(3);
const pageSizes = [3, 5, 10, 20, 50]
const total = ref(0)

async function getMyExperiments() {
        const res = await listItem(studentInfo.id, keyword.value)
        if (res.data.code === 200) {
                tableData.value = res.data.data.list;
                total.value = res.data.data.total;
        } else {
                ElMessage.error(res.data.msg);
        }
}

function handleSearch() {
        getMyExperiments()
}

const tableDataPagedList = computed(() => {
        return tableData.value.slice((currentPage.value - 1) * currentSize.value, currentPage.value * currentSize.value)
})

function toDetail(id) {
        router.push(`/student/detail/${id}`)
}

onMounted(() => {
        getMyExperiments();
        document.title = "我的实验_慧图工坊";
})

</script>

<style lang="less" scoped>
.head {
    width: 50%;
    display: flex;
    justify-content: space-between;
    margin-left: 30px;
}

.pager {
    margin-top: 10px;
    display: flex;
    justify-content: center;

    .slot {
        display: flex;
        justify-content: space-around;

        .sizes {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 90px;
            margin-left: 10px;
        }

        .total {
            width: 50px;
            display: flex;
            padding: 10px;
            align-items: center;

            .label {
                padding: 0 5px;
            }

            .value {
                padding: 0 5px;
                color: #008c8c;
            }
        }

        .jumper {
            display: flex;
            padding: 5px;
            align-items: center;

            .label {
                padding: 5px;
            }

            .input {
                padding: 5px;
            }
        }
    }
}

.list {
    display: flex;
    flex-direction: column;

    .item {
        display: flex;
        justify-content: space-between;
        padding: 10px;

        .label {
            width: 80px;
            font-weight: bold;
            color: #999;
        }

        .value {
            width: 400px;
            font-weight: bold;
        }
    }
}

@media (max-width: 768px) {
    .head{
        width: 90%;
    }
    .content{
        width: 90%;
        margin-left: 0px;
    }
    .head el-input{
        width: 100%;
    }
}
</style>
