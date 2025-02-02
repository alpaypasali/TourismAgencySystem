PGDMP                      |            TourismAgency    15.7    16.3 5    6           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            7           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            8           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            9           1262    25031    TourismAgency    DATABASE     �   CREATE DATABASE "TourismAgency" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "TourismAgency";
                postgres    false            �            1259    25033    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    role character varying NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    25032    Users_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Users_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."Users_id_seq";
       public          postgres    false    215            :           0    0    Users_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public."Users_id_seq" OWNED BY public.users.id;
          public          postgres    false    214            �            1259    25085    hotels    TABLE     �  CREATE TABLE public.hotels (
    hotel_id integer NOT NULL,
    hotel_name character varying NOT NULL,
    city character varying NOT NULL,
    district character varying NOT NULL,
    full_address character varying NOT NULL,
    email character varying NOT NULL,
    phone character varying NOT NULL,
    star_rating integer NOT NULL,
    has_free_parking boolean NOT NULL,
    has_spa boolean NOT NULL,
    has_24_7_room_service boolean NOT NULL,
    pension_type_id integer NOT NULL
);
    DROP TABLE public.hotels;
       public         heap    postgres    false            �            1259    25084    hotels_hotel_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hotels_hotel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.hotels_hotel_id_seq;
       public          postgres    false    223            ;           0    0    hotels_hotel_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.hotels_hotel_id_seq OWNED BY public.hotels.hotel_id;
          public          postgres    false    222            �            1259    25042    pension_types    TABLE     d   CREATE TABLE public.pension_types (
    id integer NOT NULL,
    name character varying NOT NULL
);
 !   DROP TABLE public.pension_types;
       public         heap    postgres    false            �            1259    25041    pension_type_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pension_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.pension_type_id_seq;
       public          postgres    false    217            <           0    0    pension_type_id_seq    SEQUENCE OWNED BY     L   ALTER SEQUENCE public.pension_type_id_seq OWNED BY public.pension_types.id;
          public          postgres    false    216            �            1259    25101    reservations    TABLE     �  CREATE TABLE public.reservations (
    reservation_id integer NOT NULL,
    hotel_id integer NOT NULL,
    room_id integer NOT NULL,
    customer_name character varying NOT NULL,
    customer_id character varying NOT NULL,
    customer_phone character varying NOT NULL,
    customer_email character varying NOT NULL,
    total_price integer NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    adult_count integer NOT NULL,
    child_count integer NOT NULL
);
     DROP TABLE public.reservations;
       public         heap    postgres    false            �            1259    25100    reservations_reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservations_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.reservations_reservation_id_seq;
       public          postgres    false    227            =           0    0    reservations_reservation_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.reservations_reservation_id_seq OWNED BY public.reservations.reservation_id;
          public          postgres    false    226            �            1259    25060 
   room_types    TABLE     a   CREATE TABLE public.room_types (
    id integer NOT NULL,
    name character varying NOT NULL
);
    DROP TABLE public.room_types;
       public         heap    postgres    false            �            1259    25059    room_types_id_seq    SEQUENCE     �   CREATE SEQUENCE public.room_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.room_types_id_seq;
       public          postgres    false    219            >           0    0    room_types_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.room_types_id_seq OWNED BY public.room_types.id;
          public          postgres    false    218            �            1259    25078    rooms    TABLE     �  CREATE TABLE public.rooms (
    room_id integer NOT NULL,
    hotel_id integer NOT NULL,
    room_type_id integer NOT NULL,
    adult_price integer NOT NULL,
    child_price integer NOT NULL,
    stock integer NOT NULL,
    bed_count integer NOT NULL,
    square_meters integer NOT NULL,
    television boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    safe boolean NOT NULL,
    projection boolean NOT NULL
);
    DROP TABLE public.rooms;
       public         heap    postgres    false            �            1259    25077    rooms_room_id_seq    SEQUENCE     �   CREATE SEQUENCE public.rooms_room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.rooms_room_id_seq;
       public          postgres    false    221            ?           0    0    rooms_room_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.rooms_room_id_seq OWNED BY public.rooms.room_id;
          public          postgres    false    220            �            1259    25094    seasons    TABLE     �   CREATE TABLE public.seasons (
    season_id integer NOT NULL,
    hotel_id integer NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);
    DROP TABLE public.seasons;
       public         heap    postgres    false            �            1259    25093    seasons_season_id_seq    SEQUENCE     �   CREATE SEQUENCE public.seasons_season_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.seasons_season_id_seq;
       public          postgres    false    225            @           0    0    seasons_season_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.seasons_season_id_seq OWNED BY public.seasons.season_id;
          public          postgres    false    224            �           2604    25088    hotels hotel_id    DEFAULT     r   ALTER TABLE ONLY public.hotels ALTER COLUMN hotel_id SET DEFAULT nextval('public.hotels_hotel_id_seq'::regclass);
 >   ALTER TABLE public.hotels ALTER COLUMN hotel_id DROP DEFAULT;
       public          postgres    false    222    223    223            �           2604    25045    pension_types id    DEFAULT     s   ALTER TABLE ONLY public.pension_types ALTER COLUMN id SET DEFAULT nextval('public.pension_type_id_seq'::regclass);
 ?   ALTER TABLE public.pension_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            �           2604    25104    reservations reservation_id    DEFAULT     �   ALTER TABLE ONLY public.reservations ALTER COLUMN reservation_id SET DEFAULT nextval('public.reservations_reservation_id_seq'::regclass);
 J   ALTER TABLE public.reservations ALTER COLUMN reservation_id DROP DEFAULT;
       public          postgres    false    226    227    227            �           2604    25063    room_types id    DEFAULT     n   ALTER TABLE ONLY public.room_types ALTER COLUMN id SET DEFAULT nextval('public.room_types_id_seq'::regclass);
 <   ALTER TABLE public.room_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    219    219            �           2604    25081    rooms room_id    DEFAULT     n   ALTER TABLE ONLY public.rooms ALTER COLUMN room_id SET DEFAULT nextval('public.rooms_room_id_seq'::regclass);
 <   ALTER TABLE public.rooms ALTER COLUMN room_id DROP DEFAULT;
       public          postgres    false    221    220    221            �           2604    25097    seasons season_id    DEFAULT     v   ALTER TABLE ONLY public.seasons ALTER COLUMN season_id SET DEFAULT nextval('public.seasons_season_id_seq'::regclass);
 @   ALTER TABLE public.seasons ALTER COLUMN season_id DROP DEFAULT;
       public          postgres    false    224    225    225            �           2604    25036    users id    DEFAULT     f   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public."Users_id_seq"'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            /          0    25085    hotels 
   TABLE DATA           �   COPY public.hotels (hotel_id, hotel_name, city, district, full_address, email, phone, star_rating, has_free_parking, has_spa, has_24_7_room_service, pension_type_id) FROM stdin;
    public          postgres    false    223   n=       )          0    25042    pension_types 
   TABLE DATA           1   COPY public.pension_types (id, name) FROM stdin;
    public          postgres    false    217   ~>       3          0    25101    reservations 
   TABLE DATA           �   COPY public.reservations (reservation_id, hotel_id, room_id, customer_name, customer_id, customer_phone, customer_email, total_price, start_date, end_date, adult_count, child_count) FROM stdin;
    public          postgres    false    227   �>       +          0    25060 
   room_types 
   TABLE DATA           .   COPY public.room_types (id, name) FROM stdin;
    public          postgres    false    219   e?       -          0    25078    rooms 
   TABLE DATA           �   COPY public.rooms (room_id, hotel_id, room_type_id, adult_price, child_price, stock, bed_count, square_meters, television, minibar, game_console, safe, projection) FROM stdin;
    public          postgres    false    221   �?       1          0    25094    seasons 
   TABLE DATA           L   COPY public.seasons (season_id, hotel_id, start_date, end_date) FROM stdin;
    public          postgres    false    225   @       '          0    25033    users 
   TABLE DATA           =   COPY public.users (id, username, password, role) FROM stdin;
    public          postgres    false    215   Z@       A           0    0    Users_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public."Users_id_seq"', 4, true);
          public          postgres    false    214            B           0    0    hotels_hotel_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hotels_hotel_id_seq', 3, true);
          public          postgres    false    222            C           0    0    pension_type_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.pension_type_id_seq', 7, true);
          public          postgres    false    216            D           0    0    reservations_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservations_reservation_id_seq', 1, true);
          public          postgres    false    226            E           0    0    room_types_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.room_types_id_seq', 4, true);
          public          postgres    false    218            F           0    0    rooms_room_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.rooms_room_id_seq', 7, true);
          public          postgres    false    220            G           0    0    seasons_season_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.seasons_season_id_seq', 4, true);
          public          postgres    false    224            �           2606    25040    users Users_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "Users_pkey" PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.users DROP CONSTRAINT "Users_pkey";
       public            postgres    false    215            �           2606    25092    hotels hotels_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.hotels
    ADD CONSTRAINT hotels_pkey PRIMARY KEY (hotel_id);
 <   ALTER TABLE ONLY public.hotels DROP CONSTRAINT hotels_pkey;
       public            postgres    false    223            �           2606    25049    pension_types pension_type_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.pension_types
    ADD CONSTRAINT pension_type_pkey PRIMARY KEY (id);
 I   ALTER TABLE ONLY public.pension_types DROP CONSTRAINT pension_type_pkey;
       public            postgres    false    217            �           2606    25108    reservations reservations_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (reservation_id);
 H   ALTER TABLE ONLY public.reservations DROP CONSTRAINT reservations_pkey;
       public            postgres    false    227            �           2606    25067    room_types room_types_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.room_types
    ADD CONSTRAINT room_types_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.room_types DROP CONSTRAINT room_types_pkey;
       public            postgres    false    219            �           2606    25083    rooms rooms_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (room_id);
 :   ALTER TABLE ONLY public.rooms DROP CONSTRAINT rooms_pkey;
       public            postgres    false    221            �           2606    25099    seasons seasons_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.seasons
    ADD CONSTRAINT seasons_pkey PRIMARY KEY (season_id);
 >   ALTER TABLE ONLY public.seasons DROP CONSTRAINT seasons_pkey;
       public            postgres    false    225            /      x����N�0���S���$EH�FAl�@G.	�U7���}*v�F����#�d��e���X6�2l��^��ݮeQ�{
&1B
��@�=��o1�5�Q?�ن@�X"�C�£7-ܢ1SuG;[1�FM��2�4�R��s�g)�c��b��}�F7�)g�M-fJ*�_J%��ś1d�,&�����:<�=c���wƚ�_K����-w��a��`�%1-����Hp���:C����qr�:"�h_)�F�pDxɒ$�K<��      )   m   x�3��))JTp��Q��K�)-�,K�2�D�s:��($�(8�&f�%�p�p*��U9�'�p�rz$�A9f`��y9�\�@���3�s܊RS!:��RS2K�b���� %�'F      3   Z   x�3�4�4�t�)H�TH,N���42144261162�405���D�"��������\N0�4202�50�52�2�uL��q��qqq ���      +   6   x�3���K�IU(����2�t�/M��9�J�2��K3KR�L8��t� �u      -   c   x�=�Q�0C��a0��.~{��s��?��m0����T$:b�^���$0
J4x���(���\�0��L$ϼ"�k`�T�����Z�.97y ~��      1   ,   x�3�4�4202�50�52�2M�L.NC��442�b���� 	p      '   ,   x�3�LL��̃�� �˘3%5/57F����W��r��qqq ��n     