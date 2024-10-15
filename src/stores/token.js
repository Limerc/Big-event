//定义store
import {defineStore} from 'pinia'
import {ref} from 'vue'
/*
    第一个参数：名字，唯一性
    第二个参数：函数，函数的内部可以定义状态的所有内容
    返回值：名字和函数的组合，可以直接使用
*/
export const useTokenStore = defineStore('token', ()=>{
    // 定义状态的内容
    // 1.响应式变量
    const token = ref('')
    // 2.修改token的函数
    const setToken = (newToken) => {
        token.value = newToken
    }
    // 3.移除token的函数
    const removeToken = () =>{
        token.value = ''
    }
    return {
        token,setToken,removeToken
    }
},{
    persist: true // 持久化存储
})