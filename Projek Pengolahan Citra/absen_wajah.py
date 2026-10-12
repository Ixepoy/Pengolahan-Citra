import cv2 
from deepface import DeepFace
import os
from datetime import datetime

#Persiapan dan Menyalakan Kamera
db_path = "database_wajah"
frame_count = 0
last_x, last_y, last_w, last_h = 0,0,0,0
recognized_name = "Tidak Dikenal"
video_capture = cv2.VideoCapture(0)
jumlah_muka_buat_daftar = 0

cv2.namedWindow('Kamera Absensi', cv2.WINDOW_NORMAL)

while True:
    ret, frame = video_capture.read()
    if not ret:
        print("Kamera Terputus!")
        break
    frame_count += 1

    if frame_count % 10 == 0:
        try:
            dfs = DeepFace.find(
                img_path = frame,
                db_path = r"C:\Users\Thinkpad X13\Documents\Coding\Projek Pengolahan Citra\database_wajah",
                model_name = "VGG-Face",
                detector_backend = "mtcnn",
                enforce_detection = False,
                anti_spoofing = True
            )
            if len(dfs) > 0 and not dfs[0].empty:
                df = dfs[0]

                last_x = int(df['source_x'][0])
                last_y = int(df['source_y'][0])
                last_w = int(df['source_w'][0])
                last_h = int(df['source_h'][0])

                path_foto = df['identity'][0]
                recognized_name = os.path.basename(path_foto).split('.')[0]
            else:
                recognized_name = "Tidak Dikenal"
        except:
            pass
    if recognized_name != "Tidak Dikenal":
        cv2.rectangle(frame, (last_x, last_y), (last_x + last_w, last_y + last_h), (0, 255, 0), (2))
        cv2.putText(frame, recognized_name, (last_x, last_y - 10), cv2.FONT_HERSHEY_SIMPLEX, 1.0, (0, 255, 0), (2))
    
    cv2.imshow('Kamera Absensi', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

        

"""
daftar_wajah_encodings = []
daftar_nama = []

# Buat opencv video capture object untuk menangkap video dari kamera
video_capture = cv2.VideoCapture(0)
current_frame = 0
small_frame = True

#buat face recognition
face_location = []
face_encodings = []
face_distances = []
match_index = []

#buat absensi
orang_yg_sudah_absen = []
tolerance_level = 0.6"""
