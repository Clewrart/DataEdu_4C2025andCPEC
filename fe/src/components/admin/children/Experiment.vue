<style lang="scss" scoped>
@import '@/styles/variables';

.container {
  padding: 20px;

  .table-container {
    @include card-style;
    padding: 20px;
  }

  .operation-buttons {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
  }
}

// 响应式调整
@media (max-width: 768px) {
  .head {
    flex-direction: column;
    align-items: stretch;

    .el-input {
      margin-right: 0;
      margin-bottom: 10px;
    }
  }

  .el-table {
    .el-table-column {
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
    <el-table :data="tableData" :scrollbar-always-on="true" style="width: 100%">
        <el-table-column prop="id" label="ID" width="60px"/>
        <el-table-column prop="name" label="实验名" width="120px" />
        <el-table-column prop="description" label="要求/说明" width="300px" />
        <el-table-column prop="teacherId" label="教师" :formatter="teacherFormatter" />
        <el-table-column prop="uploadFileType" label="上传文件类型" width="120px" />
        <el-table-column prop="judgeUrl" label="评判接口地址" width="150px" />
        <el-table-column prop="totalUploadCount" label="可上传次数" width="100px" />
        <el-table-column prop="deadlineTime" label="截止时间" />
        <el-table-column prop="createdTime" label="创建时间" />
        <el-table-column label="操作" fixed="right" width="250px">
            <template #default="scope">
                <el-button type="primary" :icon="Edit" @click="editCallBack(scope.row)">修改</el-button>
                <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#ff6c37"
                    title="确定删除吗?" @confirm="handleDelete(scope.row.id)" @cancel="cancelEvent">
                    <template #reference>
                        <el-button type="danger" :icon="Delete" @click="">删除</el-button>
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


    <el-dialog v-model="editVisible" width="500" title="修改" :before-close="handleClose">
        <el-input type="text" v-model="editForm.name" placeholder="实验名" />
        <el-select v-model="editForm.teacherId" placeholder="Select" style="width: 240px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <el-input type="textarea" v-model="editForm.description" placeholder="要求/说明" />
        <el-input type="text" v-model="editForm.uploadFileType" placeholder="上传文件要求" />
        <el-input type="text" v-model="editForm.judgeUrl" placeholder="评判接口地址" />
        <el-input type="number" v-model.number="editForm.totalUploadCount" placeholder="可上传次数" />
        <TimePicker label="请选择截止时间" :time="editForm.deadlineTime" @change="handleSelectEditTime"> </TimePicker>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="handleEdit">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="addVisible" width="500" title="添加" :before-close="handleClose">
        <el-input type="text" v-model="addForm.name" placeholder="实验名" />
        <el-input type="textarea" v-model="addForm.description" placeholder="要求/说明" />
        <el-select v-model="addForm.teacherId" placeholder="Select" style="width: 240px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <TimePicker label="请选择截止时间" :time="addForm.deadlineTime" @change="handleSelectAddTime"></TimePicker>

        <el-input type="number" v-model.number="addForm.totalUploadCount" placeholder="可上传次数" />
        <el-input type="text" v-model="addForm.uploadFileType" placeholder="上传文件类型" />
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
    <el-dialog v-model="imageListVisible" width="500" title="添加" :before-close="handleClose">
        <div class="item" v-for="item in imageList" :key="item">
            <el-image :src="item" style="width: 100px; height: 100px" />
        </div>
    </el-dialog>

</template>

<script setup>
import { ref, onMounted,computed } from "vue";
import { listItem, deleteItem, updateItem, addItem } from "@api/experiment";
import { listAll as getTeacherList } from "@api/teacher";
import { ElMessage } from "element-plus";
import { Search, Plus, Edit, Delete } from "@element-plus/icons-vue"
const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(3);
const pageSizes = [3,5, 10, 20, 50]
const total = ref(0)


const teachers = ref([])
function teacherFormatter(id, column, cellValue) {
    console.log(`output-cellValue`, cellValue)
    return teachers.value.find(teacher => teacher.id === cellValue)?.name;
}
const options = computed(() => {
    return teachers.value.map(item=>({label:item.name,value:item.id}))
})

function getStudentList() {
    getTeacherList().then(res => {
        if (res.data.code === 200) {
            teachers.value = res.data.data.list;
        }
    })
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
    addForm.value = shallowCopyObjectWithEmptyValues(tableData.value[0]);
});

const editVisible = ref(false);
const addVisible = ref(false);




function handleDelete(id) {
    deleteItem(id).then((res) => {
        if (res.data.code === 200) {
            getStudentList();
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
    getStudentList();
}

function handleEdit() {
    const formData = new FormData();
    for (const key of Object.keys(editForm.value)) {
        if (editForm.value[key]) {
            formData.append(key, editForm.value[key])
        }
    }


    updateItem(formData).then((res) => {
        if (res.data.code === 200) {
            getStudentList();
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

    addItem(formData).then((res) => {
        if (res.data.code === 200) {
            ElMessage({ message: "添加成功", type: "success" })
            addVisible.value = false
            getStudentList();
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
    console.log(`output-time`,time)
    editForm.value.deadlineTime = time;
}

function handleSelectAddTime(time) {
    console.log(`output-time`, time)
    addForm.value.deadlineTime = time;
}

</script>

<style lang="less" scoped>
.head {
    width: 50%;
    display: flex;
    justify-content: space-between;
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
            width: 100px;
        }

        .total {
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
</style>
