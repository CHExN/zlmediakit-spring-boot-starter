package io.github.lunasaw.zlm.hook.service;

import io.github.lunasaw.zlm.hook.param.*;
import lombok.extern.slf4j.Slf4j;

/**
 * ZLM Hook 服务抽象实现
 *
 * @author CHEaN
 */
@Slf4j
public abstract class AbstractZlmHookService implements ZlmHookService {

    @Override
    public void onFlowReport(HookParamForOnFlowReport param) {

    }

    @Override
    public HookResultForOnHttpAccess onHttpAccess(HookParamForOnHttpAccess param) {
        return HookResultForOnHttpAccess.success();
    }

    @Override
    public HookResult onPlay(HookParamForOnPlay param) {
        return HookResult.success();
    }

    @Override
    public HookResultForOnPublish onPublish(HookParamForOnPublish param) {
        return HookResultForOnPublish.success();
    }

    @Override
    public void onRecordMp4(HookParamForOnRecordMP4 param) {

    }

    @Override
    public HookResultForOnRtspAuth onRtspAuth(HookParamForOnRtspAuth param) {
        return HookResultForOnRtspAuth.success();
    }

    @Override
    public HookResultForOnRtspRealm onRtspRealm(HookParamForOnRtspRealm param) {
        return HookResultForOnRtspRealm.success();
    }

    @Override
    public HookResult onShellLogin(HookParamForOnShellLogin param) {
        return HookResult.success();
    }

    @Override
    public void onStreamChanged(HookParamForOnStreamChanged param) {

    }

    @Override
    public HookResultForOnStreamNoneReader onStreamNoneReader(HookParamForOnStreamNoneReader param) {
        return HookResultForOnStreamNoneReader.success();
    }

    @Override
    public void onStreamNotFound(HookParamForOnStreamNotFound param) {

    }

    @Override
    public void onServerStarted(HookParamForOnServerStarted param) {

    }

    @Override
    public void onServerExited(HookParamForOnServerExited param) {

    }

    @Override
    public void onServerKeepLive(HookParamForOnServerKeepalive param) {

    }

    @Override
    public void onSendRtpStopped(HookParamForOnSendRtpStopped param) {

    }

    @Override
    public void onRtpServerTimeout(HookParamForOnRtpServerTimeout param) {

    }

}
