import request from '@/utils/request.js'
//文章分类列表查询
export const articleCategoryListService = ()=>{
    //const tokenStore = useTokenStore()
    //通过配置携带token；在pinia中定义的数据都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/category')
}
export const articleCategoryAddService = (categorydata)=>{
    return request.post('/category',categorydata)
}

//文章分类修改
export const articleCategoryUpdateService = (categorydata)=>{
    return request.put('/category',categorydata)
}

//文章分类删除
export const articleCategoryDeleteService = (id)=>{
    return request.delete('/category?id='+id)
}

//文章列表查询
export const articleListService = (params)=>{
    // queryString参数利用params传入
    return request.get('/article',{params:params})
}

//文章发布
export const articleAddService = (articleData)=>{
    return request.post('/article',articleData);
}

//更新文章
export const articleUpdateService = (articleData)=>{
    return request.put('/article',articleData);
}

//删除文章
export const articleDeleteService = (id)=>{
    return request.delete('/article?id='+id)
}

//获取文章详情
export const articleDetailService = (id)=>{
    return request.get('/article/detail?id='+id)
}