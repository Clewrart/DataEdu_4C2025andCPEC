<style lang="scss" scoped>
@import '@/styles/student';

.header {
  margin-bottom: 20px;

  .back .el-button {
    transition: $transition-all;

    &:hover {
      transform: translateX(-3px);
    }
  }
}

.el-row {
  display: flex;
  gap: 20px;
  @include responsive-layout;
}

.experiment {
  @include card-style;

  > div {
    display: flex;
    margin: 12px 0;
    align-items: center;

    .label {
      font-weight: bold;
      min-width: 120px;
      color: $text-primary;
    }

    .value {
      font-weight: bold;
      color: $highlight-color;
    }
  }

  .name {
    font-size: 24px;
    margin-bottom: 20px;
    color: $text-primary;
  }

  .teacherDocs, .uploadFile {
    margin-top: 20px;

    .list .item {
      @include card-style;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 15px;
      margin-bottom: 10px;

      .origin-name {
        font-weight: bold;
        color: $highlight-color;
        flex: 1;
      }

      .el-button {
        margin-left: 10px;
        transition: $transition-all;

        &:hover {
          transform: scale(1.1);
        }
      }
    }
  }

  .uploadFile .label {
    margin-bottom: 15px;

    .el-button {
      padding: 10px 20px;
      font-size: 16px;
    }
  }
}

.rankList {
  @include card-style;
  height: fit-content;
  weight: 50%;

  .title {
    font-size: 20px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
    color: $text-primary;
    padding-bottom: 10px;
    border-bottom: 1px solid $border-color;
  }

  .list {
    max-height: 500px;
    overflow-y: auto;
    padding: 0 10px;

    .header, .item {
      display: flex;
      justify-content: space-between;
      padding: 12px 0;
      border-bottom: 1px dashed $border-color;

      > div {
        flex: 1;
        text-align: center;
      }
    }

    .header {
      font-weight: bold;
      color: $warning-color;
      position: sticky;
      top: 0;
      background: $card-bg;
    }

    .item {
      color: $rank-color;
      transition: $transition-all;

      &:hover {
        background-color: rgba($primary-color, 0.05);
      }
    }
  }
}

.deadline-warning {
  color: $danger-color;
  font-weight: bold;
  padding: 10px;
  background-color: rgba($danger-color, 0.1);
  border-radius: $border-radius-small;
  text-align: center;
}

:deep(.el-dialog) {
  border-radius: $border-radius-base;

  .el-upload-dragger {
    padding: 30px;
    border-radius: $border-radius-base;

    .el-icon--upload {
      font-size: 60px;
      color: $primary-color;
      margin-bottom: 15px;
    }

    .el-upload__text {
      font-size: 16px;

      em {
        color: $primary-color;
        font-style: normal;
      }
    }
  }

  .uploadFileName {
    margin-top: 15px;

    .name {
      color: $highlight-color;
      font-weight: bold;
      margin-top: 5px;
    }
  }
}

@media (max-width: 768px) {
  .experiment > div {
    flex-direction: column;
    align-items: flex-start;

    .label {
      margin-bottom: 5px;
    }
  }

  .rankList {
    width: 120%;
    margin-top: 10px;

    .list {
      height: 70%;
      max-height: 300px;
      width: 100%;
    }
  }
}
</style>
<template>
    <div class="header" style="margin: 10px;">
        <div class="back">
            <el-button type="warning" :icon="Back" size="small" @click="router.back()">返回</el-button>
        </div>
    </div>
    <el-row>
        <el-col :span="12" style="border: 0px solid black; padding: 10px; " >
            <div class="experiment">
                <div class="name">
                    <div class="label">
                        实验名:
                    </div>
                    <div class="value">
                        {{ experiment.name }}
                    </div>
                </div>
                <div class="description">
                    <div class="label">
                        实验描述:
                    </div>
                    <div class="value">
                        {{ experiment.description }}
                    </div>
                </div>
                <div class="teacherDocs">
                    <div class="list">
                        <div class="item" v-for="doc, index in teacherDocuments" :key="index">
                            <div class="origin-name">
                                {{ doc.originName }}
                            </div>
                            <el-button type="success" :icon="Download" circle size="mini"
                                @click="getFile(doc.saveName)"></el-button>
                        </div>
                    </div>
                </div>
                <div class="uploadCount">
                    <div class="label">剩余上传次数</div>
                    <div class="value">{{ restCount }}</div>
                </div>
                <div class="uploadFileType">
                    <div class="label">需要上传文件类型</div>
                    <div class="value">{{ experiment.uploadFileType }}</div>
                </div>

                <div class="time">
                  <div class="label">创建时间</div>
                  <div class="value">{{ experiment.createdTime }}</div>
               </div>

                <div class="time">
                  <div class="label">截止时间</div>
                  <div class="value">{{ experiment.deadlineTime }}</div>
                </div>

                <div class="score">
                  <div class="label" style="font-size: 20px">最后一次上传得分</div>
                  <div class="value" style="font-size: 50px" >{{ currentES.score }}</div>
                </div>
                <div class="rank">
                    <div class="label">最后一次得分排名（同分按时间排序）</div>
                    <div class="value" style="font-size: 30px">{{ myRank>0?myRank:'暂无' }}</div>
                </div>

              <div class="uploadFile">
                <div class="label">
                  <!--超过截止时间，禁用按钮 -->
                  <div v-if="isAfterDeadline===false">
                    <el-button type="primary" :icon="Plus" size="mini" @click="uploadDocumentVisible = true"
                               :disabled="isUploadDisabled">点此上传作业</el-button>
                  </div>

                  <div v-if="isAfterDeadline" class="deadline-warning">
                    任务时间点已过！
                  </div>
                </div>
              </div>
              </div>

                    <div class="value">
                      <p>已上传作业记录（从新到旧）：</p>
                        <div v-if="studentDocuments.length === 0">
                            你暂未上传相关作业。
                        </div>
                      <div id="card">
                        <div class="list">
                            <div class="item" v-for="document in studentDocuments">
                              <div class="item">
                                <div class="origin-name">
                                    {{ document.originName }}
                                  <el-button type="success" :icon="Download" circle size="small"
                                             @click="getFile(document.saveName)"></el-button>
                                  <el-button type="danger" :icon="Delete" circle size="small"
                                             @click="deleteTeacherDocument(document.id)"></el-button>
                                </div>
                              </div>
                            </div>
                        </div>
                       </div>
                    </div>
        </el-col>
        <div class="rankList">
          <div class="title">本次实验排行榜</div>
          <div class="list">
              <div class="header">
                  <div class="rank">排名</div>
                  <div class="name">姓名</div>
                  <div class="score">分数</div>
              </div>
              <div class="item" v-for="item in currentRankList" :key="item.name">
                  <div class="rank">{{ item.rank }}</div>
                  <div class="name">{{ item.name }}</div>
                  <div class="score">{{ item.score }}</div>
              </div>
          </div>
        </div>
    </el-row>
    <el-dialog v-model="uploadDocumentVisible" width="350" title="请按照要求上传本次实验内容" :before-close="handleClose">
        <el-upload class="upload-demo" :on-change="handleFileChange" :show-file-list="false" :auto-upload="false" drag
            :multiple="experiment.uploadFileType==='image/*'" :accept="experiment.uploadFileType">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
                拖动文件至此或者 <em>点击上传</em>
            </div>
            <template #tip>
                <div class="el-upload__tip">
                    上传本次实验的作业,系统会给出分数和排名。
                </div>
                <div class="uploadFileName" v-if="uploadFileList.length>0">
                    <div class="label">当前选择的文件：</div>
                    <div class="fileList">
                        <div class="name" v-for="file,index in uploadFileList" :key="index">{{ file.name }}</div>
                    </div>
                </div>
            </template>
        </el-upload>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="uploadDocumentVisible = false">取消</el-button>
                <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled" icon-color="#ff6c37"
                    title="确定上传吗?请注意上传成功后,会消耗一次上传机会" @confirm="handleUploadDocument">
                    <template #reference>
                        <el-button type="primary" :icon="Plus" @click="" :disabled="uploadFileList.length===0"
                            :loading="uploadDocumentLoading">上传</el-button>
                    </template>
                </el-popconfirm>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { getItemById as getExperimentByID } from "@api/experiment"
import { ref, onMounted, computed } from "vue"
import { getDocuments, uploadDocument, deleteDocument } from "@api/document"
import { getFile } from "@api/files"
import { getUserInfo } from "@utils/access"
import { ElMessage } from "element-plus"
import { judge } from "@api/judge"
import {
    getItemByExperimentIdAndStudentId,
    updateES,
    getRank,
    rankList
} from "@api/experiment_student"


import { Plus, Back, Delete, Download } from "@element-plus/icons-vue"
import { useRouter } from "vue-router"
import index from "@compos/teacher/Index.vue";
import { addUnit } from "element-plus/es/utils/index.mjs"

const router = useRouter()
const props = defineProps({
    id: Number
})

const studentInfo = getUserInfo();

const experiment = ref({})

const studentDocuments = ref([])
async function getExperiment() {
    const res = await getExperimentByID(props.id)
    if (res.data.code === 200) {
        experiment.value = res.data.data
    }
    const teacherDocsRes=await getDocuments(props.id,experiment.value.teacherId,"teacher")
    if(teacherDocsRes.data.code===200){
        teacherDocuments.value=teacherDocsRes.data.data
    }
}


async function getStudentDocuments() {
    const res = await getDocuments(props.id, studentInfo.id, studentInfo.role)
    if (res.data.code === 200) {
        studentDocuments.value = res.data.data
    }
}

const deleteTeacherDocument = async (id) => {
    const res = await deleteDocument(id)
    if (res.data.code === 200) {
        studentDocuments.value = studentDocuments.value.filter(item => item.id !== id)
        ElMessage.success("删除成功")
    }

}
// const uploadDocumentFile = ref(null);
const uploadFileList= ref([])
const uploadDocumentVisible = ref(false)
const uploadDocumentLoading = ref(false)

function handleFileChange(file, fileList) {
    const fileType = experiment.value?.uploadFileType || '';
    if (!file || !file.raw) {
        ElMessage.error("文件读取失败");
        return;
    }

    if (fileType === '.csv' || fileType === '.json') {
        uploadFileList.value = [file.raw];
    } else if (fileType === 'image/*') {
        //多图模式
        uploadFileList.value = fileList.map(f => f.raw);
    } else {
        ElMessage.error("不支持的文件类型");
    }
}



async function handleUploadDocument() {
    uploadDocumentLoading.value = true;
    let score = 0;
    try {
        const judge_res = await judge(experiment.value.judgeUrl, uploadFileList.value)
        score = judge_res.data.score;
    } catch (error) {
        console.log(`output-error`, error)
        return ElMessage.error("评分失败")
    }
    finally {
        uploadDocumentLoading.value = false;
    }

    const formData = new FormData()
    formData.append("experimentId", props.id)
    formData.append("userId", studentInfo.id)
    formData.append("role", studentInfo.role)
    for (const file of uploadFileList.value) {
        console.log(`output-file`,file)
        formData.append("files", file)
    }

    const res = await uploadDocument(formData)
    if (res.data.code === 200) {
        await getStudentDocuments()
    }
    const esForm = new FormData()
    esForm.append("experimentId", props.id)
    esForm.append("studentId", studentInfo.id)
    esForm.append("score", score)
    esForm.append("uploadCount", currentES.value.uploadCount + 1)
    try {
        await updateES(esForm)
    } catch (error) {
        ElMessage.error("系统错误,请删除文件后重试")
    }
    await getES()
    await getCurrentRankList();
    await getMyRank();

    ElMessage.success(`
        本次提交得分:${score},
        本次提交排名:${myRank.value}/${currentRankList.value.length}
    `)
    uploadDocumentVisible.value = false
    uploadDocumentLoading.value = false;
}

const currentES = ref({})
async function getES() {
    const res = await getItemByExperimentIdAndStudentId(props.id, studentInfo.id)
    if (res.data.code === 200) {
        currentES.value = res.data.data
    }
}

const restCount = computed(() => {
    return experiment.value.totalUploadCount - currentES.value.uploadCount
})


const myRank = ref(-1)
async function getMyRank() {
    const res = await getRank(props.id, studentInfo.id);
    if (res.data.code === 200) {
        myRank.value=res.data.data
    }
}

const currentRankList = ref([])
async function getCurrentRankList() {
    const res = await rankList(props.id);
    if (res.data.code === 200) {
        const rawList = res.data.data;//原始数据
        //计算排名
        const rankedList = calculateRank(rawList);
        //更新列表
        currentRankList.value = rankedList;
    }
}
//后端不会改，前端重塑
function calculateRank(rankList) {
    let rank = 1; //目前排名
    let prevScore = null; //上一个分数
    let sameRankCount = 0; //同分人数累计
    const result = [];

    rankList.forEach((item, index) => {
        if (item.score !== prevScore) {
            rank += sameRankCount; //跳过相同分数的排名
            sameRankCount = 1;     //重置同分计数
        } else {
            sameRankCount++; //分数相同，同分计数+1
        }

        prevScore = item.score; //更新上一分数

        //添加排名字段
        result.push({
            ...item,
            rank,
        });
    });

    return result;
}


const teacherDocuments=ref([])

onMounted(async () => {

    await getExperiment();
    await getES()
    await getMyRank();
    await getCurrentRankList();
    await getStudentDocuments();
    document.title = "实验详情_慧图工坊";
})

//超时禁用提交
const isAfterDeadline = computed(() => {
    const deadline = new Date(experiment.value.deadlineTime)
    const now = new Date()
    return now > deadline
})

</script>

<style lang="less" scoped>
.experiment {
    >div {
        display: flex;
        margin: 2px 0;

        .label {
            font-weight: bold;
            margin-right: 20px;
            color: #333;
        }

        .value {
            font-weight: bold;
            color: #0a864c
        }
    }

    .name {
        font-size: 30px;
    }
    .teacherDocs{
        display:block;
        .title{
            margin:20px auto;
            font-size: 25px;
            font-weight: bold;
            width: 300px;
        }
        .list .item{
            width: 300px;
            background:#eee;
            padding: 5px 30px;
            border-radius: 10px;
            display:flex;
            justify-content:space-between;
            .origin-name{
                font-weight: bold;
                color: #25b0f3;
                margin-right: 20px;
            }
        }
    }

    .uploadFile {
        display: block;

        .label {
            width: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 25px;
            color: #9f5608;

            div {
                margin-right: 20px;
            }
        }

        .list {
            margin-top: 5px;

            .item {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .origin-name {
                    font-weight: bold;
                    color: #25b0f3;
                    margin-right: 20px;
                }
            }
        }
    }



}
.rankList{
  width: 400px;
    .title{
        font-weight: bold;
        text-align: center;
        font-size: 30px;
        margin-bottom: 20px;
    }
    .list{
        height:400px;
        overflow-y: scroll;
    }
    .header,
    .item{
        width: 300px;
        margin: 20px auto;
        display: flex;
        justify-content: space-between;
        font-size: 20px;
        font-weight: bold;
    }
    .header{
        color: #9f5608;
    }
    .item{
        color:#008c8c
    }
}
.uploadFileName{
    .name{
        color:#25b0f3;
        font-weight: bold;
    }
}


.deadline-warning {
    color: red;
    font-weight: bold;
    margin-top: 10px;
}


@media screen and (max-width: 750px) {
    .el-row {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

  .value{
        font-weight: bold;
        color: #0a864c;
  }

    .experiment-details,
    .rank-section {
        width: 95%;
        max-width: 500px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .experiment > div {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }


    .rankList .title {
        font-size: 25px;
    }

    .rankList .list {
        height: auto;
        overflow-y: auto;
    }

    .rankList .header,
    .rankList .item {
        justify-content: space-between;
        width: 90%;
    }
}
</style>