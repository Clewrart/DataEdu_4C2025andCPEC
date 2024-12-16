<template>
    <div class="head">
            <el-input type="text" v-model="keyword" style="margin-right:15px;"></el-input>
            <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
            <el-button type="primary" :icon="Plus" @click="addCallBack">添加</el-button>
    </div>
    <div class="head" style="margin-top: 10px;">
            <div class="buttons">
                <el-button type="success" :icon="Plus" @click="importVisible = true">导入</el-button>
                <el-button type="warning" :icon="Select" @click="handleMultipleSelect" v-if="!selectToolsVisible">批量选择</el-button>
              <el-button type="info" :icon="Close" @click="selectToolsVisible=false" v-if="selectToolsVisible">取消选择</el-button>
            </div>
    </div>
    <div class="head" style="margin-top: 10px;">
            <div class="buttons">
                <el-button type="primary" :icon="Finished" @click="handleSelectAll" v-if="selectToolsVisible" >全选</el-button>
                <el-button type="info" :icon="SemiSelect" @click="handleSelectNone" v-if="selectToolsVisible" >全不选</el-button>
                <el-popconfirm v-if="selectToolsVisible" confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#ff6c37"
                        title="确定删除已勾选的学生吗?" @confirm="handleDeleteBatch" @cancel="cancelEvent">
                        <template #reference>
                            <el-button  type="danger" :icon="Delete">删除已选</el-button>
                        </template>
                </el-popconfirm>
            </div>
    </div>
    <div class="content" style="margin-left: 30px;margin-right: 30px;">
        <el-table :data="tableData" :scrollbar-always-on="true" style="width: 100%">
            <el-table-column label="是否选择" v-if="selectToolsVisible">
                <template #default="scope">
                    <el-checkbox v-model="scope.row.selected" size="large" />
                </template>
            </el-table-column>
            <el-table-column prop="id" label="ID" />
            <el-table-column prop="number" label="学号" />
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="createdTime" label="创建时间" />
            <el-table-column label="操作" fixed="right" width="90px">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" @click="editCallBack(scope.row)">修改</el-button><span class="span1"><br></span>
                    <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#ff6c37"
                        title="确定删除吗?" @confirm="handleDelete(scope.row.id)" @cancel="cancelEvent">
                        <template #reference>
                            <el-button type="danger" :icon="Delete" @click="">删除</el-button>
                        </template>
                    </el-popconfirm>
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
        <el-input type="text" v-model="editForm.number" placeholder="学号" />
        <el-input type="text" v-model="editForm.name" placeholder="姓名" />
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
        <el-input type="text" v-model="addForm.number" placeholder="学号" />
        <el-input type="text" v-model="addForm.name" placeholder="姓名" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="handleAdd">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog v-model="importVisible" width="350" title="通过Excel表格导入学生" :before-close="handleClose">
        <el-upload class="upload-demo" :on-change="handleFileChange" :show-file-list="false" :auto-upload="false" drag
            :multiple="false" accept=".xls,.xlsx">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
                拖动文件至此或者 <em>点击上传</em>
            </div>
            <template #tip>
                <div class="el-upload__tip">
                    导入学生的excel表格。表格文件的sheet1表头应包含id、学号、姓名
                </div>
                <div class="uploadFileName" v-if="studentExcel">
                    <span class="label">当前选择的文件：</span><span class="name">{{ studentExcel.name }}</span>
                </div>
            </template>
        </el-upload>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="importVisible = false">取消</el-button>
                <el-button 
                type="success" 
                @click="uploadStudentExcel" 
                :loading="uploadStudentButtonLoading" 
                :disabled="!studentExcel">
                    导入
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { listItem, deleteItem, updateItem, addItem, uploadStudent ,deleteBatch} from "@api/student";
import { ElMessage } from "element-plus";
import { Search, Plus, Edit, Delete ,Select, Finished, SemiSelect, Close} from "@element-plus/icons-vue"
const tableData = ref([]);

const keyword = ref("");

const currentPage = ref(1);
const currentSize = ref(5);
const pageSizes = [5, 10, 20, 50]
const total = ref(0)

const selectToolsVisible=ref(false)

function handleMultipleSelect(){
    selectToolsVisible.value = true;
}

function handleSelectAll(){
    tableData.value.forEach(item => {
        item.selected = true;
    });
}


function handleSelectNone(){
    tableData.value.forEach(item => {
        item.selected = false;
    });
}

async function handleDeleteBatch() {
    const ids = tableData.value.filter(item => item.selected).map(item=>item.id)
    const res = await deleteBatch(ids);
    if (res.data.code===200) {
        ElMessage({ message: "删除成功", type: "success" })
        getStudentList();
        selectToolsVisible.value = false;
    
    } else {
        ElMessage({ message: "删除失败", type: "error" })
    }
    
}


function getStudentList() {
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
    document.title = "学生管理_数据科学与机器学习实验系统";
});

const editVisible = ref(false);
const addVisible = ref(false);




function handleDelete(id) {
    deleteItem(id).then((res) => {
            getStudentList();
            ElMessage({ message: res.data.msg, type: res.data.code === 200? "success" : "error" })

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

const studentExcel = ref(null)

const importVisible = ref(false)
function handleFileChange(e) {
    console.log(`output-e`, e)
    studentExcel.value = e.raw;
    console.log(`output-studentExcel.value`, studentExcel.value)
}

const uploadStudentButtonLoading=ref(false)

function uploadStudentExcel() {
    if (!studentExcel.value) {
        ElMessage({ message: "请选择文件", type: "error" })
        return
    }
    uploadStudentButtonLoading.value = true;
    
    uploadStudent(studentExcel.value).then((res) => {
        if (res.data.code === 200) {
            ElMessage({ message: "导入成功", type: "success" })
            importVisible.value = false
            getStudentList();
        } else {
            ElMessage({ message: "导入失败", type: "error" })
        }
        uploadStudentButtonLoading.value = false;
    }).catch(err => {
        ElMessage({ message: "导入失败", type: "error" })
        uploadStudentButtonLoading.value = false;
    });
}



</script>

<style lang="less" scoped>
.head {
    width: 50%;
    display: flex;
    justify-content: space-around;
    margin-left: 30px;
    .input{
        flex: 2;
        margin-right: 10px;
    }
    .buttons{
        flex:6
    }
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

.uploadFileName {
    padding: 2vh 0;
    font-size: 14px;
    .name {
        color: #1d5002;
        font-weight: bold;
    }
    .label{
        color: #999;
        font-weight: bold;
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
