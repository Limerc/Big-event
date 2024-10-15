<script setup>
import { ref } from 'vue'
//这里无需进行其它绑定
const userInfo = ref({
    old_pwd:'',
    new_pwd:'',
    re_pwd:''
})

//校验密码函数
const checkrepassword = (rule, value, callback) => {
    if(value===''){
        callback(new Error('请再次输入密码'))
    }else if(value!== userInfo.value.new_pwd){ 
        callback(new Error('两次输入密码不一致'))
    }else{
        callback()
    }
}

const rules = {
    old_pwd: [
        { required: true, message: '请输入旧密码', trigger: 'blur' },
        {
            min:5,max:16,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    new_pwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            min:5,max:16,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    re_pwd: [
        {validator: checkrepassword, trigger:'blur'}
    ]
}

import {userPasswordUpdateService} from '@/api/user.js';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
const router = useRouter();
//修改密码
const updatePassword = async ()=>{
    //调用接口
    let result = await userPasswordUpdateService(userInfo.value)
    ElMessage.success(result.message ? result.message : '修改成功')
    //重新登录 
    router.push('/login')
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>修改密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="旧密码" prop="old_pwd">
                        <el-input v-model="userInfo.old_pwd" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="new_pwd">
                        <el-input v-model="userInfo.new_pwd" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="确认新密码" prop="re_pwd">
                        <el-input v-model="userInfo.re_pwd" show-password></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatePassword">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>