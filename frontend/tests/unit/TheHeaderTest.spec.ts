import TheHeader from "@/components/TheHeader.vue";
import getWrapper from "./utils/factory";

/**
 * Test for TheHeader.vue
 * Testing the menuVisible variable and the loggedIn variable
 */
describe("TheHeader.vue", () => {
  it("renders menuVisible variable when passed", () => {
    const wrapper = getWrapper(
      TheHeader,
      {},
      { visible: true },
      { loggedIn: true }
    );
    expect(wrapper.find("#menu-toggle").exists()).toBeTruthy;
  });

  it("renders error when an error is passed", () => {
    const wrapper = getWrapper(
      TheHeader,
      {},
      { visible: false },
      { loggedIn: true }
    );
    expect(wrapper.find("#error").exists()).toBeTruthy;
  });

  it("renders loggedIn variable when passed", () => {
    const wrapper = getWrapper(
      TheHeader,
      {},
      { visible: true },
      { loggedIn: true }
    );
    expect(wrapper.find("#login-check").exists()).toBeTruthy;
  });
});
