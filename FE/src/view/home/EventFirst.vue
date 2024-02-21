<template>
    <div class="caro-detail-info">
        <img src="@/assets/img/home/snsevent.png" alt="SNS-event" class="sns-event">
        <img src="@/assets/img/help/cancel.png" alt="cancel" class="cancel-vector">
    </div>
</template>

<script> 
    export default {
    }
</script>

<style scoped>
.caro-detail-info {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50vw;
    height: 80vh;
}
.sns-event {
    max-width: 100%;
    max-height: 100%;
    border-radius: 30px;
    border: 5px solid white;
}   

.cancel-vector {
    position: absolute;
    top: 20px;
    right: 20px;
    width: 20px;
    height: 20px;
}
</style>