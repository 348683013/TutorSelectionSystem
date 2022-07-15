export default [
    {
        path:'/condition',
        component:()=>import('@/views/Condition/Condition.vue'),
        meta:{isShow:true},
        children:[
            {
                path:'distribution',
                component:()=>import('@/views/Distribution/Distribution.vue'),
                meta:{isShow:true}
            }
        ]
    },
    {
        path:'/admin',
        component:()=>import('@/views/Admin/Admin.vue'),
        meta:{isShow:true}
    },
    {
        path:'/teacher',
        component:()=>import('@/views/Teacher/Teacher.vue'),
        meta:{isShow:true},
        children:[
            {
                path:'edit',
                component:()=>import('@/views/Edit/Edit.vue'),
                meta:{isShow:true}
            }
        ]
    },
    {
        path:'/student',
        component:()=>import('@/views/Student/Student.vue'),
        meta:{isShow:true},
        children:[
            {
                path:'edit',
                component:()=>import('@/views/Edit/Edit.vue'),
                meta:{isShow:true}
            }
        ]
    },
    {
        path: '/apply',
        component: () => import('@/views/Apply/Apply.vue'),
        meta: { isShow: true },
        children:[
            {
                path:'detail',
                component:()=>import('@/views/Apply/StudentInfo/StudentInfo.vue'),
                meta:{isShow:true}
            }
        ]
    },
    {
        path: '/user',
        component: () => import('@/views/User/User.vue'),
        meta: { isShow: true },
        children: [
            {
                path: 'change',
                component: () => import('@/views/User/Change/Change.vue'),
                meta: { isShow: true }
            }
        ]
    },
    {
        path: '/pick1',
        component: () => import('@/views/Pick/Pick.vue'),
        meta: { isShow: true }
    },
    {
        path: '/pick2',
        component: () => import('@/views/Pick/Pick.vue'),
        meta: { isShow: true }
    },

    {
        path: '/list',
        component: () => import('@/views/List/List.vue'),
        meta: { isShow: true },
        children: [
            {
                path: 'detail',
                component: () => import('@/views/List/Detail/Detail.vue'),
                meta: { isShow: true }
            }
        ]
    },
    {
        path: '/login',
        // 路由懒加载
        component: () => import('@/views/Login/Login.vue'),
        meta: { isShow: false }
    },
    {
        path: '/home',
        component: () => import('@/views/Home/Home.vue'),
        meta: { isShow: true }
    },
    {
        path: '/process',
        component: () => import('@/views/Process/Process.vue'),
        meta: { isShow: true }
    },
    {
        path: '/',
        redirect: '/login'
    }
]