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
        <el-input type="text" v-model="keyword" style="margin-right:15px;"> </el-input>
            <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
            <el-button type="primary" :icon="Plus" @click="addCallBack">添加</el-button>
    </div>
    <div class="content" style="margin-left: 30px;margin-right: 30px;">
        <el-table :data="tableData" :scrollbar-always-on="true" style="width: 100%">
            <el-table-column prop="id" label="ID" width="60px"/>
            <el-table-column prop="name" label="实验名" width="160px"/>
            <el-table-column prop="description" label="要求/说明" width="300px" />
            <el-table-column prop="uploadFileType" label="上传文件类型" width="120px" />
            <el-table-column prop="judgeUrl" label="评判接口地址" width="180px" />
            <el-table-column prop="deadlineTime" label="截止时间" width="120px" />
            <el-table-column prop="createdTime" label="创建时间"  width="120px"/>
          <el-table-column label="操作" fixed="right" width="220px">
            <template #default="scope">
              <el-button
                  type="success"
                  :icon="Edit"
                  @click="toExperimentDetail(scope.row.id)"
                  style="margin-bottom: 5px">查看实验情况与详情
              </el-button>
              <div style="display: flex; gap: 8px">
                <el-button type="primary" :icon="Edit" @click="editCallBack(scope.row)">修改</el-button>
                <el-popconfirm
                    confirm-button-text="确定"
                    cancel-button-text="取消"
                    :icon="InfoFilled"
                    icon-color="#ff6c37"
                    title="确定删除吗?"
                    @confirm="handleDelete(scope.row.id)"
                    @cancel="cancelEvent"
                >
                  <template #reference>
                    <el-button type="danger" :icon="Delete">删除</el-button>
                  </template>
                </el-popconfirm>
              </div>
            </template>
          </el-table-column>
        </el-table>
    </div>
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


    <el-dialog v-model="editVisible" width="350" title="修改" :before-close="handleClose">
        <el-input type="text" v-model="editForm.name" placeholder="实验名" />
        <el-input type="textarea" v-model="editForm.description" placeholder="要求/说明" />
        <el-select v-model="editForm.uploadFileType" placeholder="请选择学生上传文件类型" style="width: 240px">
            <el-option v-for="item in fileTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-input type="text" v-model="editForm.judgeUrl" placeholder="评判接口地址" />
        <el-input type="number" :min="1" v-model.number="editForm.totalUploadCount" placeholder="可上传次数" />
        <TimePicker label="截止时间" :time="editForm.deadlineTime" @change="handleSelectEditTime"> </TimePicker>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="handleEdit">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="addVisible" width="350" title="添加" :before-close="handleClose">
        <el-input type="text" v-model="addForm.name" placeholder="实验名" />
        <el-input type="textarea" v-model="addForm.description" placeholder="要求/说明" />

        <TimePicker label="截止时间" :time="addForm.deadlineTime" @change="handleSelectAddTime"></TimePicker>

        <el-input type="number" :min="1" v-model.number="addForm.totalUploadCount" placeholder="可上传次数" />
        <el-select v-model="addForm.uploadFileType" placeholder="请选择学生上传文件类型" style="width: 240px">
            <el-option v-for="item in fileTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-input type="text" v-model="addForm.judgeUrl" placeholder="评判接口地址" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="handleAdd">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog v-model="imageListVisible" width="350" title="添加" :before-close="handleClose">
        <div class="item" v-for="item in imageList" :key="item">
            <el-image :src="item" style="width: 100px; height: 100px" />
        </div>
    </el-dialog>

</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { listItemByTeacherId, deleteItem, updateItem, addItem } from "@api/experiment";
import { ElMessage } from "element-plus";
import { Search, Plus, Edit, Delete } from "@element-plus/icons-vue"
import { getUserInfo } from "@utils/access"
import { useRouter } from "vue-router"
const router=useRouter()

const teacher = getUserInfo()
console.log(`output-teacher`, teacher)

const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(3);
const pageSizes = [3, 5, 10, 20, 50]
const total = ref(0)


const teachers = ref([])
function teacherFormatter(id, column, cellValue) {
    console.log(`output-cellValue`, cellValue)
    return teachers.value.find(teacher => teacher.id === cellValue)?.name;
}
const options = computed(() => {
    return teachers.value.map(item => ({ label: item.name, value: item.id }))
})

function getExperimentList() {
    listItemByTeacherId(teacher.id,keyword.value, currentPage.value, currentSize.value).then((res) => {
        if (res.data.code === 200) {
            tableData.value = res.data.data.list;
            total.value = res.data.data.total;

        }
    });

}


function handlePaginationChange() {
    getExperimentList();
}

onMounted(async () => {
    await getExperimentList();
    addForm.value = shallowCopyObjectWithEmptyValues(tableData.value[0]);
    document.title = "教师实验项目_慧图工坊";
});

const editVisible = ref(false);
const addVisible = ref(false);


function handleDelete(id) {
    deleteItem(id).then((res) => {
        if (res.data.code === 200) {
            getExperimentList();
            ElMessage({ message: "删除成功", type: "success" })
        } else {
            ElMessage({ message: "删除失败", type: "error" })
        }

    });
}


const editForm = ref({

})

const addForm = ref({

})

function editCallBack(row) {
    editForm.value = Object.assign({}, row)
    console.log(editForm.value);
    editVisible.value = true
}

function addCallBack() {
    addVisible.value = true
}


function handleSearch() {
    getExperimentList();
}

function handleEdit() {
    const formData = new FormData();
    for (const key of Object.keys(editForm.value)) {
        if (editForm.value[key]) {
            formData.append(key, editForm.value[key])
        }
    }

    formData.append("teacherId", teacher.id)
    updateItem(formData).then((res) => {
        if (res.data.code === 200) {
            getExperimentList();
            ElMessage({ message: "修改成功", type: "success" })
            editVisible.value = false
        }
    });
}
function handleAdd() {
    const formData = new FormData();
    for (const key of Object.keys(addForm.value)) {
        if (addForm.value[key]) {
            formData.append(key, addForm.value[key]);
        }
    }
    formData.append("teacherId", teacher.id)

    addItem(formData).then((res) => {
        if (res.data.code === 200) {
            ElMessage({ message: "添加成功", type: "success" })
            addVisible.value = false
            getExperimentList();
        }
    });

}


function handleClose(done) {
    done();
}

function shallowCopyObjectWithEmptyValues(obj) {
    const copy = {};
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            copy[key] = "";
        }
    }
    return copy;
}

const imageList = ref([])


function handleSelectEditTime(time) {
    console.log(`output-time`, time)
    editForm.value.deadlineTime = time;
}

function handleSelectAddTime(time) {
    console.log(`output-time`, time)
    addForm.value.deadlineTime = time;
}

const fileTypeOptions = [
    {
        label: "csv文件",
        value: ".csv"
    },
    {
        label: "图片文件",
        value: "image/*"
    },
    {
        label: "CocoJson文件",
        value: ".json"
    },
]

function toExperimentDetail(id) {
    router.push(`/teacher/experimentDetail/${id}`)
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
    .head {
        width: 90%;
    }
    .content {
        width: 90%;
        margin-left: 0px;
    }
    .head el-input {
        width: 100%;
    }
}
</style>
