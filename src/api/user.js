//导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData) =>{
    //registerData是json格式数据
    //需借助于UrlSearchParams将json格式数据转换为请求参数
    const params = new URLSearchParams();
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/user/register',params);
}

//提供调用登录接口的函数
export const userLoginService = (loginData) =>{
    const params = new URLSearchParams();
    for(let key in loginData){
        params.append(key,loginData[key]);
    }
    return request.post('/user/login',params);
}

//获取用户详细信息
export const userInfoService = ()=>{
    return request.get('/user/userinfo');
}

//修改个人信息
export const userInfoUpdateService = (updateData)=>{
    return request.put('/user/update',updateData)
}

//修改用户头像
export const userAvatarUpdateService = (avatarUrl)=>{
    const params = new URLSearchParams();
    params.append('avatarUrl',avatarUrl)
    return request.patch('/user/updateAvatar',params)
}

//修改密码
export const userPasswordUpdateService = (passwordData)=>{
    return request.patch('/user/updatePwd',passwordData)
}