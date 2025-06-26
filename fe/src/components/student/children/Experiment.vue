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

    &:active {
      transform: translateY(0);
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
      &:nth-child(n+4) {
        display: none;
      }
    }
  }
}
</style>
<template>
    <div class="head">
        <el-input type="text" v-model="keyword" style="margin-right:15px;"></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
    </div>
    <el-table :data="tableData" :scrollbar-always-on="true" style="width: 100%">
        <el-table-column prop="id" label="ID" width="60px" />
        <el-table-column prop="name" label="实验名" width="120px" />
        <el-table-column prop="description" label="要求/说明" width="350px" />
        <el-table-column prop="teacherId" label="教师" :formatter="teacherFormatter" width="120px" />
        <el-table-column prop="uploadFileType" label="上传文件类型" width="120px" />
        <el-table-column prop="totalUploadCount" label="可上传次数" width="120px" />
        <el-table-column prop="deadlineTime" label="截止时间" width="150px" />
        <el-table-column prop="createdTime" label="创建时间" width="150px" />
        <el-table-column label="操作" fixed="right" width="150px">
            <template #default="scope">
                <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#ff6c37"
                    title="确定加入该实验吗?" @confirm="handleParticipate(scope.row.id)" @cancel="cancelEvent">
                    <template #reference>
                        <el-button
                            type="primary"
                            :icon="Plus"
                            @click=""
                            style="width: 87px;"
                            :disabled="isAfterDeadline(scope.row.deadlineTime)">加入
                        </el-button>
                    </template>
                </el-popconfirm>
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
                            <el-option v-for="size in pageSizes" :key="size" :label="size + '/页'" :value="size" />
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
                            <el-input v-model.number="currentPage" @change="handlePaginationChange"
                                style="width: 50px;" />
                        </div>
                    </div>
                </div>
            </template>
        </el-pagination>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { listItem, deleteItem, updateItem, addItem } from "@api/experiment";
import { listAll as getTeacherList } from "@api/teacher";
import { ElMessage } from "element-plus";
import { Search, Plus } from "@element-plus/icons-vue";
import { getUserInfo } from "@utils/access";
import { participate } from "@api/experiment_student";

const studentInfo = getUserInfo();

const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(3);
const pageSizes = [3, 5, 10, 20, 50];
const total = ref(0);

const teachers = ref([]);
function teacherFormatter(id, column, cellValue) {
    console.log(`output-cellValue`, cellValue);
    return teachers.value.find(teacher => teacher.id === cellValue)?.name;
}

function getStudentList() {
    getTeacherList().then(res => {
        if (res.data.code === 200) {
            teachers.value = res.data.data.list;
        }
    });
    listItem(keyword.value, currentPage.value, currentSize.value).then((res) => {
        if (res.data.code === 200) {
            tableData.value = res.data.data.list;
            total.value = res.data.data.total;
        }
    });
}

function handlePaginationChange() {
    getStudentList();
}

onMounted(async () => {
    await getStudentList();
    document.title = "待选实验_慧图工坊";
});

function handleSearch() {
    getStudentList();
}

//判断是否超过截止时间
function isAfterDeadline(deadline) {
    const deadlineDate = new Date(deadline);
    const currentDate = new Date();
    return currentDate > deadlineDate;  //如果当前时间超过截止时间，返回true
}

async function handleParticipate(experimentId) {
    try {
        const res = await participate(experimentId, studentInfo.id);
        ElMessage.success("加入成功");
    } catch (error) {
        console.error(error);
        ElMessage.error("加入失败, 您已加入过该实验");
    }
}
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
