FROM gcr.io/google_containers/kube-registry-proxy:0.4

ENV PORT 5000

COPY boot /bin/boot

EXPOSE 5000
