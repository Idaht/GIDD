import ActivityInformation from "@/views/ActivityInformation.vue";
import getWrapper from "./utils/factory";

/**
 * Test for ActivityInformation.vue
 * Testing the signedUp variable
 */
describe("ActivityInformation.vue", () => {
  it("increases number of participants and shows other buttons when true", () => {
    const signedUp = true;
    const wrapper = getWrapper(
      ActivityInformation,
      {},
      { isSignedUp: signedUp }
    );
    expect(wrapper.find("#signing-up").exists()).toBeTruthy;
  });
});
