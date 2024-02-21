// pinia아 생성
import { createPinia } from 'pinia';
//pinia storage 저장 plugin
import piniaPersist from 'pinia-plugin-persist';

export const useStore = createPinia();
useStore.use(piniaPersist);
