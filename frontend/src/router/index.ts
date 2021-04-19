import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "../views/Home.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    component: () => import("../views/About.vue"),
  },
  {
    path: "/log-in",
    name: "LogIn",
    component: () => import("../views/LogIn.vue"),
  },
  {
    path: "/activity-feed",
    name: "ActivityFeed",
    meta: {
      requiresAuth: true,
    },
    component: () => import("../views/ActivityFeed.vue"),
  },
  {
    //Catch all makes router redirect all unknown URLs to the PageNotFound view
    path: "/:catchAll(.*)",
    name: "PageNotFound",
    component: () => import("../views/PageNotFound.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
