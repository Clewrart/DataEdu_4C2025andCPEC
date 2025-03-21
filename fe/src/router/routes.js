

const routes = [
    {
        path: "/admin",
        name: 'admin',
        component: () => import("@compos/admin/Index.vue"),
        children: [
            {
                path: "user",
                component: () => import("@compos/admin/children/User.vue")
            },
            {
                path: "student",
                component: () => import("@compos/admin/children/Student.vue")
            },
            {
                path: "teacher",
                component: () => import("@compos/admin/children/Teacher.vue")
            },
            {
                path: "experiment",
                component: () => import("@compos/admin/children/Experiment.vue")
            }

        ]
    },
    {
        path: "/student",
        name: 'student',
        component: () => import("@compos/student/Index.vue"),
        children: [
            {
                path: "experiment",
                component: () => import("@compos/student/children/Experiment.vue")
            },
            {
                path: "me",
                component: () => import("@compos/student/children/Me.vue")
            },
            {
                path: "detail/:id",
                props:true,
                component: () => import("@compos/student/children/ExperimentDetail.vue")
            }
        ]

    },
    {
        path: "/teacher",
        name: 'teacher',
        component: () => import("@compos/teacher/Index.vue"),
        children: [
            {
                path: "student",
                component: () => import("@compos/teacher/children/Student.vue")
            },
            {
                path: "experiment",
                component: () => import("@compos/teacher/children/Experiment.vue")
            },
            {
                path: "experimentDetail/:id",
                props:true,
                component: () => import("@compos/teacher/children/ExperimentDetail.vue")
            }
        ]
    },

    {
        path: "/",
        name: 'login',
        component: () => import("@compos/Login.vue")
    },

    {
        path: "/help",
        name: 'help',
        component: () => import("@compos/help.vue")
    },

    {
        path: "/about",
        name: 'about',
        component: () => import("@compos/about.vue")
    },
    {
        path: '/:catchAll(.*)', // 捕获所有未匹配的路由
        name: 'NotFound',
        component: () => import("@compos/NotFound.vue"),
    },

]


export default routes;
