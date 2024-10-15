import {defineStore} from 'pinia'
import {ref} from 'vue'
const useUserInfoStore = defineStore('userinfo',()=>{
    //定义状态相关内容
    const info = ref({})
    //set方法
    const setInfo = (newInfo)=>{
        info.value   = newInfo
    }
    //remove方法
    const removeInfo = ()=>{
        info.value = {}
    }
    //返回状态相关内容
    return {info,setInfo,removeInfo}
},{persist: true})

//默认导出
export default useUserInfoStore