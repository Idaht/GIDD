import { DefineComponent } from "@vue/runtime-core";
import { shallowMount, VueWrapper } from "@vue/test-utils";

/**
 * Shallow mounts a component, so that it can be used for testing
 * @param component to be shallowounted
 * @param props an object containing all the props e.g {msg: "Example"}
 * @param data an object containing all the necessary data e.g {msg: "Data example"}
 * @returns VueWrapper<any> the shallowMounted component to be tested.
 */
const getWrapper = (
  component: DefineComponent<
    Record<string, unknown>,
    Record<string, unknown>,
    any
  >,
  props: Record<string, unknown>,
  data: Record<string, unknown>
): VueWrapper<any> => {
  return shallowMount(component, {
    props: { ...props },
    data() {
      return {
        ...data,
      };
    },
  });
};

export default getWrapper;
