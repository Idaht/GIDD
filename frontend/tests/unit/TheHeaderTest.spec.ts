import TheHeader from "@/components/TheHeader.vue";
import getWrapper from "./utils/factory";

/**
 * Test for TheHeader.vue
 * Testing the menuVisible variable and the loggedIn variable
 */
describe("TheHeader.vue", () => {
  it("renders menuVisible variable when passed", () => {
    const visible = true;
    const wrapper = getWrapper(TheHeader, {}, { menuVisible: visible });
    expect(wrapper.find("#menu-toggle").exists()).toBeTruthy;
  });

  it("renders error when an error is passed", () => {
    const visible = false;
    const wrapper = getWrapper(TheHeader, {}, { menuVisible: visible });
    expect(wrapper.find("#error").exists()).toBeTruthy;
  });

  it("renders loggedIn variable when passed", () => {
    const loggedIn = true;
    const wrapper = getWrapper(TheHeader, {}, { loggedIn });
    expect(wrapper.find("#login-check").exists()).toBeTruthy;
  });
});
